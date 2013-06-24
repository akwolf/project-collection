应用布置步骤

一、进入conf目录下，打开 wrapper.conf

需要修改的信息有以下几处，请参照wrapper.conf，说明如下

#请修改D:/jdk1.6.0_35为您机器上的jdk目录，本应用必须是64位jdk1.6以上版本
#请务必注意路径分隔用的是 正斜杠 "/" 而不是通常的反斜杠

第5行  wrapper.java.command=D:/jdk1.6.0_35/bin/java
第16行 wrapper.java.classpath.2=D:/jdk1.6.0_35/lib/tools.jar


二、进入bin目录下，打开jdbc.properties
需要修改的为4-6行

#数据库服务器所在IP:端口/数据库名
jdbc.url=jdbc:sybase:Tds:127.0.0.1:2630/smpp
#数据库用户名
jdbc.username=DBA
#数据库用户密码
jdbc.password=sql


三、进入bin目录双击InstallApp-NT.bat生成windows服务(注意请用右键以管理员身份运行)

四、进入服务控制台(如在运行中输入services.msc)，找到 SMPP SMSC Service 服务右键启动

五、如能正常启用，说明服务配置成功，请至logs目录下查看log日志

六、如需删除服务请至bin目录下以管理员身份运行UninstallApp-NT.bat脚本


七、注意smpp.jpg指明了目前配置Afaria收发端口为同一个9999