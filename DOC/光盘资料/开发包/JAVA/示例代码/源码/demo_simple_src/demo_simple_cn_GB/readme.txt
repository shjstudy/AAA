本项目为Eclipse导出项目，使用时直接导入即可

本项目为807国标协议示例代码

使用串口连接方式运行本项目时，必须先按如下方式配置rxtx环境

----------RXTXComm_start----------
1.Windows 

①rxtxParallel.dll、rxtxSerial.dll拷贝到%JAVA_HOME%\bin目录下

②将RXTXcomm.jar%JAVA_HOME%\jre\lib\ext\RXTXcomm.jar 

2.Linux 

①复制librxtxSerial.so,librxtxParallel.so到%JAVA_HOME%/lib/$(ARCH)/ 

②复制RXTXcomm.jar到%JAVA_HOME%/ext 

③定义驱动类后将javax.comm.properties放在应用程式的根目录下

----------RXTXComm_end----------
