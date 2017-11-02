using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace WindowsFormsApplication1
{
    public class ViewStorageBin
    {
        public string Operation { get; set; }//”add”-入库增加数量, “minus”-出库减少数量
        public int ID { get; set; }
        public int PlateID { get; set; }//料盘号，暂定与库位号一致
        public string Type { get; set; }//”bottle”, “lid”
        public string Color { get; set; }//”light”, “dark”
        public int Number { get; set; }
    }
    public class ResultData
    {
        public string Result { get; set; }
        public ErrorCode Code { get; set; }
    }
    public enum ErrorCode
    {
        OK = 0,
        UserNotLogined,
        WrongParameter,
        Exception
    }
    public class ViewStackerState
    {
        public string PickTime { get; set; }
        public string StackerPositon { get; set; }
        public double StackerWalkingCurrent { get; set; }
        public double StackerWalkingSpeed { get; set; }
        public double StackerLiftingCurrent { get; set; }
        public double StackerLiftingSpeed { get; set; }
        public double StackerStackingCurrent { get; set; }
        public double StackerStackingSpeed { get; set; }
    }

    public class ViewStackerAlarmInfo
    {
        public string PickTime { get; set; }
        public int AlarmType { get; set; }
        public string AlarmInfo { get; set; }
    }

    public class ViewAGVState
    {
        public string PickTime { get; set; }
        public float AGVPosition { get; set; }
        public float AGVPower { get; set; }
    }

    public class ViewAGVAlarmInfo
    {
        public string PickTime { get; set; }
        public int AlarmType { get; set; }
        public string AlarmInfo { get; set; }
    }

    public class PartSupplementResult              //在补料结束或者出现错误时返回补料结果
    {
        public int RequestID { get; set; }
        public PartSupplementResultCode ResultCode { get; set; }            //如果补料不成功，MES会过一段时间再发送同一ID的Request，直到错误解决
        public string RequestResultTime { get; set; }        //只有成功完成补料，该属性才有值，否则为null
    }

    public enum PartSupplementResultCode
    {
        e0000,      //补料成功，无错误
        e0001,      //仓库中缺乏指定物料
        e0002,      //立体仓库错误，从立体仓库中出料失败
        e0003,      //AGV错误，无法完成补料
        //...补充其余可能出现的错误
    }

}
