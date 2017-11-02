using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using IServices;
using Util;
using System.Data;

namespace BLL
{
    public class BLLBase
    {

        private IServices.IBLLBaseService bll;

        private string cnKey;
        public BLLBase()
        {
            bll = Server.GetChannel<IServices.IBLLBaseService>();
            cnKey = "";

        }
        public BLLBase(string CnKey)
        {
            bll = Server.GetChannel<IServices.IBLLBaseService>();
            cnKey = CnKey;
        }


        /// <summary>
        /// 使用指定连接名,执行指定命令ID的非查询SQL语句
        /// </summary>
        /// <param name="connectionName">连接名</param>
        /// <param name="commandID">命令ID</param>
        /// <param name="parameters">参数</param>
        /// <returns>影响行数</returns>
        public int ExecNonQuery(string commandID, params DataParameter[] parameters)
        {
            try
            {

                return bll.ExecNonQuery(cnKey, commandID, parameters);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// 使用指定连接名,执行指定命令ID的非查询SQL语句
        /// </summary>
        /// <param name="connectionName">连接名</param>
        /// <param name="commandID">命令ID</param>
        /// <param name="parameters">参数</param>
        /// <returns>影响行数</returns>
        public int ExecNonQueryTran(string commandID, params DataParameter[] parameters)
        {
            try
            {
                return bll.ExecNonQueryTran(cnKey, commandID, parameters);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        /// <summary>
        /// 使用指定连接名,开启事务执行一组SQL命令
        /// </summary>
        /// <param name="connectionName">连接名</param>
        /// <param name="commandIDs">命令ID数组</param>
        /// <param name="parameters">参数列表,与命令ID对应</param>
        /// <returns>影响行数</returns>
        public int ExecTran(string[] commandIDs, List<DataParameter[]> parameters)
        {
            try
            {
                return bll.ExecTran(cnKey, commandIDs, parameters);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        /// <summary>
        /// 使用指定连接名,执行指定命令ID的查询SQL语句
        /// </summary>
        /// <param name="connectionName">连接名</param>
        /// <param name="commandID">命令ID</param>
        /// <param name="parameters">参数</param>
        /// <returns>结果集</returns>
        public DataTable FillDataTable(string commandID)
        {
            try
            {
                return bll.FillDataTable(cnKey, commandID, new DataParameter[] { new DataParameter("{0}", "1=1") });
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        public DataTable FillDataTable(string commandID, params DataParameter[] parameters)
        {
            try
            {
                return bll.FillDataTable(cnKey, commandID, parameters);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        public DataSet FillDataSet(string commandID, params DataParameter[] parameters)
        {
            try
            {
                return bll.FillDataSet(cnKey, commandID, parameters);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// 使用指定连接名,执行指定命令ID的查询SQL语句,并返回第一行第一列
        /// </summary>
        /// <param name="connectionName">连接名</param>
        /// <param name="commandID">命令ID</param>
        /// <param name="parameters">参数</param>
        /// <returns>第一行第一列</returns>
        public object ExecScalar(string commandID, params DataParameter[] parameters)
        {
            try
            {
                return bll.ExecScalar(cnKey, commandID, parameters);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        /// <summary>
        /// 使用指定连接名,执行指定命令ID的分页查询SQL语句
        /// </summary>
        /// <param name="connectionName">连接名</param>
        /// <param name="commandID">命令ID</param>
        /// <param name="currentPage">当前第几页</param>
        /// <param name="pageSize">每页大小</param>
        /// <param name="recordCount">总记录条数</param>
        /// <param name="parameters">参数</param>
        /// <returns>结果集</returns>
        public DataTable GetDataPage(int pageIndex, int pageSize, string filter, string orderField, string strPrimaryKey, string strTableView, string strQueryFields)
        {
            try
            {
                DataTable dtResult = bll.GetDataPage(cnKey, pageIndex, pageSize, filter, orderField, strPrimaryKey, strTableView, strQueryFields);
                return dtResult;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        /// <summary>
        /// 查询分页
        /// </summary>
        /// <param name="commandID"></param>
        /// <param name="currentPage"></param>
        /// <param name="pageSize"></param>
        /// <param name="TotalCount"></param>
        /// <param name="parameters"></param>
        /// <returns></returns>
        public DataTable GetDataPage(string commandID, int currentPage, int pageSize, out int TotalCount, out int PageCount, params DataParameter[] parameters)
        {
            try
            {
                return bll.GetDataPage(cnKey, commandID, currentPage, pageSize, out TotalCount, out PageCount, parameters);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        /// <summary>
        /// 获取表TableName的行数
        /// </summary>
        /// <param name="TableName"></param>
        /// <param name="filter"></param>
        /// <returns></returns>
        public int GetRowCount(string TableName, string filter)
        {
            try
            {
                return bll.GetRowCount(cnKey, TableName, filter);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public string GetAutoCode(string PreName, DateTime dtime, string Filter)
        {
            try
            {
                return bll.GetAutoCode(cnKey, PreName, dtime, Filter);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public string GetAutoCodeByTableName(string PreName, string TableName, DateTime dtime, string Filter)
        {
            try
            {
                return bll.GetAutoCodeByTableName(cnKey, PreName, TableName, dtime, Filter);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //动态生成ID
        public string GetNewID(string TableName, string ColumnName, string Filter)
        {
            try
            {
                return bll.GetNewID(cnKey, TableName, ColumnName, Filter);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        public string GetFieldValue(string TableName, string FieldName, string Filter)
        {
            try
            {
                return bll.GetFieldValue(cnKey, TableName, FieldName, Filter);
            }
            catch (Exception ex)
            {
                throw ex;
            }

        }
        public DataTable GetRecord(string move, string TableName, string Filter, string PrimaryKey, string ID)
        {
            try
            {
                return bll.GetRecord(cnKey, move, TableName, Filter, PrimaryKey, ID);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
        /// 主从表新增修改,一个主表,对应多个从表。
        /// </summary>
        /// <param name="commandIDs">一个主表commandID,一个从表对应两个commandID: delete,insert 
        /// 0:表示主表,1-2:表示第一个从表,3-4:表示第二个从表
        /// </param>
        /// <param name="parameter">主表参数</param>
        /// <param name="PrimarySubKey">从表关键字 0:表示第一个从表关键字,1:表示第二个从表关键字...</param>
        /// <param name="dtSub">从表DataTable,需与PrimarySubKey对应 0:表示第一个从表Datatable,1:表示第二个从表Datatable</param>
        /// <returns></returns>
        public int ExecTran(string[] commandIDs, DataParameter[] parameter, string PrimaryKey, DataTable[] dtSub)
        {
            try
            {
                return bll.ExecTranTable(cnKey, commandIDs, parameter, PrimaryKey, dtSub);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }


        public int ExecTran(string[] commandIDs, string PrimaryKey, DataTable[] dtSub)
        {
            try
            {
                return bll.ExecTranTable(cnKey, commandIDs, PrimaryKey, dtSub);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void BatchInsertTable(DataTable dt, string TableName)
        {
            try
            {
                bll.BatchInsertTable(cnKey, dt, TableName);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}


