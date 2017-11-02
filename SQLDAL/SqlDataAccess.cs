using System;
using System.Collections.Generic;

using System.Text;
using System.Data;
using Microsoft.ApplicationBlocks.Data;
using System.Configuration;
using System.Data.SqlClient;
using System.Data.Common;
using System.Text.RegularExpressions;
using Util;
using IDAL;


namespace SQLDAL
{
    public class SqlDataAccess : IDataAccess
    {
        #region 使用SQL命令执行增删改查操作
       
        private string connectionName;
        private string connectionString;

        public DataTable GetCommandTable()
        {
           


            return null;
        }


        public string ConnectionName
        {
            get
            {
                return connectionName;
            }
            set
            {
                this.connectionName = value;
            }
        }

        public string ConnectionString
        {
            get { return connectionString; }
            set { this.connectionString = value; }
        }

        /// <summary>
        /// 将传入的参数转换为SqlParameter类型，并将命令中的占位符用实际值替换
        /// </summary>
        /// <param name="parameters"></param>
        /// <returns></returns>
        protected SqlParameter[] ConvertParam(object[] parameters, ref string commandText, CommandType commandType)
        {
            if (commandType == CommandType.StoredProcedure)
            {
                commandText = commandText.Trim(' ', '\r', '\n');
            }
            if (parameters == null || parameters.Length == 0)
            {
                return null;
            }
            List<SqlParameter> sqlParamList = new List<SqlParameter>();
            foreach (object p in parameters)
            {
                SqlParameter sqlP = null;
                if (p is IDataParameter)
                {
                    IDataParameter dbP = (IDataParameter)p;
                    sqlP = new SqlParameter(dbP.ParameterName, dbP.Value);
                    sqlP.Direction = dbP.Direction;
                    sqlP.DbType = dbP.DbType;

                    if (dbP.SourceColumn != null)
                    {
                        sqlP.SourceColumn = dbP.SourceColumn;
                    }
                }
                else if (p is DataParameter)
                {
                    DataParameter dbP = (DataParameter)p;
                    sqlP = new SqlParameter(dbP.ParameterName, dbP.Value);
                }

                if (sqlP != null)
                {
                    string paramName = sqlP.ParameterName.Trim();
                    if (paramName.StartsWith("@"))//指定参数名参数
                    {
                        sqlParamList.Add(sqlP);
                    }
                    else if (Regex.IsMatch(paramName, @"\{\d+\}"))//占位符参数
                    {
                        commandText = commandText.Replace(paramName, (sqlP.Value == null || sqlP.Value == DBNull.Value ? "null" : sqlP.Value.ToString()));
                    }
                }
            }
            return sqlParamList.ToArray();
        }

        /// <summary>
        /// 用参数值将带参数的SQL命令替换为没有参数的SQL命令(用于跟踪SQL语句)
        /// </summary>
        /// <param name="commandText">SQL命令</param>
        /// <param name="param">参数</param>
        /// <returns></returns>
        protected string GetNoParamCommandText(string commandID, string commandText, SqlParameter[] param)
        {
            if (string.IsNullOrEmpty(commandText))
            {
                return string.Empty;
            }

            string preCmd = (string.IsNullOrEmpty(commandID) ? "" : "\r\n命令ID: " + commandID + "  ") + "SQL语句: ";
            string cmdText = commandText;

            if (param != null && param.Length > 0)
            {
                foreach (SqlParameter p in param)
                {
                    if (p.DbType == DbType.Binary)
                    {
                        continue;
                    }
                    Regex regex = new Regex(p.ParameterName, RegexOptions.IgnoreCase);
                    cmdText = regex.Replace(cmdText, (p.Value == null || p.Value == DBNull.Value ? "null" : "'" + p.Value.ToString() + "'"));
                }
            }

            return preCmd + cmdText;
        }

        public int ExecNonQuery(string commandID,params object[] parameters)
        {
            string cmdText = GetCommandText(commandID);
            CommandType commandType = GetCommandType(commandID);
            SqlParameter[] param = ConvertParam(parameters, ref cmdText, commandType);
            Util.Log.Debug(GetNoParamCommandText(commandID, cmdText, param));

            return SqlHelper.ExecuteNonQuery(ConnectionString, commandType, cmdText, param);
        }
        public int ExecNonQueryTran(string commandID, params object[] parameters)
        {
            int retval = 0;
            using (SqlConnection connection = new SqlConnection(ConnectionString))
            {
                connection.Open();
                SqlTransaction transaction = connection.BeginTransaction();
                try
                {

                    string cmdText = GetCommandText(commandID);
                    CommandType commandType = GetCommandType(commandID);
                    SqlParameter[] param = (parameters == null ? null : ConvertParam(parameters, ref cmdText, commandType));

                    Util.Log.Debug(GetNoParamCommandText(commandID, cmdText, param));

                    retval = SqlHelper.ExecuteNonQuery(transaction, GetCommandType(commandID), cmdText, param);


                    transaction.Commit();
                    if (connection.State == ConnectionState.Open)
                    {
                        connection.Close();
                    }
                }
                catch (Exception ex)
                {
                    retval = 0;
                    transaction.Rollback();
                    if (connection.State == ConnectionState.Open)
                    {
                        connection.Close();
                    }
                    throw ex;
                }
            }
            return retval;
        }

        public int ExecTran(string[] commandIDs, List<object[]> parameters)
        {
            int retval = 0;

            using (SqlConnection connection = new SqlConnection(ConnectionString))
            {
                connection.Open();
                SqlTransaction transaction = connection.BeginTransaction();
                try
                {
                    for (int i = 0; i < commandIDs.Length; i++)
                    {
                        string cmdText = GetCommandText(commandIDs[i]);
                        CommandType commandType = GetCommandType(commandIDs[i]);
                        SqlParameter[] param = (parameters == null ? null : ConvertParam(parameters[i], ref cmdText, commandType));

                        Util.Log.Debug(GetNoParamCommandText(commandIDs[i], cmdText, param));

                        int ret = SqlHelper.ExecuteNonQuery(transaction, GetCommandType(commandIDs[i]), cmdText, param);
                        retval = retval + ret;
                    }
                    transaction.Commit();
                    if (connection.State == ConnectionState.Open)
                    {
                        connection.Close();
                    }
                }
                catch (Exception ex)
                {
                    retval = 0;
                    transaction.Rollback();
                    if (connection.State == ConnectionState.Open)
                    {
                        connection.Close();
                    }
                    throw ex;
                }
            }

            return retval;
        }

        public DataTable FillDataTable(string commandID,  params object[] parameters)
        {

            string cmdText = GetCommandText(commandID);
            CommandType commandType = GetCommandType(commandID);
            SqlParameter[] param = ConvertParam(parameters, ref cmdText, commandType);
            Util.Log.Debug(GetNoParamCommandText(commandID, cmdText, param));

            DataTable dtData = SqlHelper.ExecuteDataset(ConnectionString, commandType, cmdText, param).Tables[0];
            dtData.TableName = "Table";
            return dtData;
        }

        public DataSet FillDataSet(string commandID,  params object[] parameters)
        {
            string cmdText = GetCommandText(commandID);
            CommandType commandType = GetCommandType(commandID);
            SqlParameter[] param = ConvertParam(parameters, ref cmdText, commandType);
            Util.Log.Debug(GetNoParamCommandText(commandID, cmdText, param));

            return SqlHelper.ExecuteDataset(ConnectionString, commandType, cmdText, param);
        }

        public IDataReader ExecDataReader(string commandID,  params object[] parameters)
        {
            string cmdText = GetCommandText(commandID);
            CommandType commandType = GetCommandType(commandID);
            SqlParameter[] param = ConvertParam(parameters, ref cmdText, commandType);
            Util.Log.Debug(GetNoParamCommandText(commandID, cmdText, param));

            return SqlHelper.ExecuteReader(ConnectionString, commandType, cmdText, param);
        }

        public object ExecScalar(string commandID,  params object[] parameters)
        {
            string cmdText = GetCommandText(commandID);
            CommandType commandType = GetCommandType(commandID);
            SqlParameter[] param = ConvertParam(parameters, ref cmdText, commandType);
            Util.Log.Debug(GetNoParamCommandText(commandID, cmdText, param));

            return SqlHelper.ExecuteScalar(ConnectionString, commandType, cmdText, param);
        }

        public DataTable GetDataPage(string commandID,   int currentPage, int pageSize, out int TotalCount,out int PageCount, params object[] parameters)
        {
            TotalCount = 0;
            PageCount = 0;
            string cmdText = GetCommandText(commandID);
            CommandType commandType = GetCommandType(commandID);
            SqlParameter[] param = ConvertParam(parameters, ref cmdText, commandType);

            //计算总记录条数
            if (commandType == CommandType.Text)
            {
                string countCmd = cmdText;
                Regex regex = new Regex("[\\s]order[\\s]", RegexOptions.IgnoreCase);
                if (regex.IsMatch(countCmd))
                {
                    Match match = regex.Match(countCmd);
                    countCmd = countCmd.Substring(0, match.Index);
                }
                if (!string.IsNullOrEmpty(countCmd))
                {
                    countCmd = "\r\n    SELECT COUNT(0) FROM (" + countCmd + ") T";
                    Util.Log.Debug(GetNoParamCommandText(string.Empty, countCmd, param));
                    TotalCount = (int)SqlHelper.ExecuteScalar(ConnectionString, commandType, countCmd, param);
                }
            }
            else if (commandType == CommandType.StoredProcedure)
            {
                throw new Exception("不支持存储过程分页查询");
            }

            Util.Log.Debug(GetNoParamCommandText(commandID, cmdText, param));
             PageCount = GetPageCount(TotalCount, pageSize);
            if (currentPage == 0 || currentPage > PageCount)
            {
                currentPage = PageCount;
            }
            if (currentPage == 0)
                currentPage = 1;

            return SqlHelper.ExecuteDataset(ConnectionString, commandType, cmdText, currentPage, pageSize, commandID, param).Tables[0];
        }
        public int GetPageCount(int TotalCount, int PageSize)
        {
            int pageCount = 1;

            pageCount = TotalCount / PageSize;
            int p = TotalCount % PageSize;
            if (p > 0)
                pageCount += 1;

            return pageCount;
        }

        public string GetCommandText(string commandID)
        {
            string cmdText = CommandStorage.GetCommandText(commandID);
            if (string.IsNullOrEmpty(cmdText))
            {
                throw new Exception(string.Format("命令{0}不存在", commandID));//未找到指定命令抛出异常
            }
            return cmdText;
        }

        public CommandType GetCommandType(string commandID)
        {
            string cmdText = GetCommandText(commandID);
            Regex regex = new Regex("[\\s](select|from|where|insert|update|delete|truncate)+[\\s]", RegexOptions.IgnoreCase);
            if (regex.IsMatch(cmdText))
            {
                return CommandType.Text;
            }
            return CommandType.StoredProcedure;
        }
        public void BatchInsertTable(DataTable dt, string TableName)
        {
            using (SqlConnection connection = new SqlConnection(ConnectionString))
            {
                //SqlBulkCopy copy = new SqlBulkCopy((SqlConnection) this.connection) {
                //    DestinationTableName = tableName
                //};
                connection.Open();
                SqlBulkCopy copy = new SqlBulkCopy((SqlConnection)connection);
                copy.DestinationTableName = "dbo." + TableName;
                foreach (DataColumn dc in dt.Columns)
                {
                    copy.ColumnMappings.Add(dc.ColumnName, dc.ColumnName);
                }
                try
                {
                    copy.WriteToServer(dt);
                    copy.Close();
                }
                catch (Exception e)
                {
                    throw e;
                }
            }
        }
        #endregion
    }
}
