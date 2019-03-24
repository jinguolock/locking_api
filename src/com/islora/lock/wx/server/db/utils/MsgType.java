package com.islora.lock.wx.server.db.utils;

/**
 * 消息类型
 * 
 * @author mam
 *
 */

public class MsgType {
	
	
	
	public static final String editPosInfo = "tms.editPosInfo";
	public static final String getPosInfo = "tms.getPosInfo";
	public static final String getAllPosInfo = "tms.getAllPosInfo";
	public static final String setAuditoriumMap = "tms.setAuditoriumMap";
	
	public static final String getAuditoriumMap = "tms.getAuditoriumMap";
	public static final String getAllSchFromTicket = "tms.getAllSchFromTicket";
	public static final String setFilmAndSplMap = "tms.setFilmAndSplMap";
	public static final String editFilmAndSplMap = "tms.editFilmAndSplMap";
	public static final String getFilmAndSplMap = "tms.getFilmAndSplMap";
	
	public static final String addTmsUser = "tms.addTmsUser";
	public static final String editTmsUser = "tms.editTmsUser";
	public static final String deleteTmsUser = "tms.deleteTmsUserByName";
	public static final String getAllTmsUser = "tms.getAllTmsUser";
	public static final String getTmsUserByName = "tms.getTmsUserByName";
	public static final String setTmsUserPwd = "tms.setTmsUserPwd";
	
	
	
	
	
	
	
	
	public static final String getAllDeviceStatus = "tms.getAllDeviceStatus";
	public static final String getAllAuditorium = "tms.getAllAuditorium";
	public static final String getAuditoriumByName = "tms.getAuditoriumByName";
	public static final String getAuditAllDevicesByName = "tms.getAuditAllDevicesByName";
	public static final String getAuditAllDevicesById = "tms.getAuditAllDevicesById";
	public static final String deleteAuditoriumByName = "tms.deleteAuditoriumByName";
	public static final String getAllDeviceStatusInAudit= "tms.getAllDeviceStatusInAudit";
	public static final String checkDcServer = "tms.checkDcServer";
	public static final String checkProjector = "tms.checkProjector";
	public static final String checkAudio = "tms.checkAudio";
	public static final String checkAutoControler = "tms.checkAutoControler";
	public static final String addAuditAndDcServer = "tms.addAuditAndDcServer";
	public static final String editAuditoriumMap = "tms.editAuditoriumMap";
	public static final String deleteAuditoriumMap = "tms.deleteAuditoriumMap"; 
	public static final String deleteFilmAndSplMap  = "tms.deleteFilmAndSplMap";
	public static final String addDcServer = "tms.addDcServer";
	public static final String addAudit = "tms.addAudit";
	public static final String editAuditAndDcServer = "tms.editAuditAndDcServer";
        public static final String editAudit = "tms.editAudit";
	public static final String addProjector = "tms.addProjector";
	public static final String editProjector = "tms.editProjector";
	public static final String deleteProjector = "tms.deleteProjector";
	public static final String addAudioProcessor = "tms.addAudioProcessor";
	public static final String editAudioProcessor = "tms.editAudioProcessor";
	public static final String deleteAudioProcessor = "tms.deleteAudioProcessor";
	public static final String addAutoControler = "tms.addAutoControler";
	public static final String editAutoControler = "tms.editAutoControler";
	public static final String deleteAutoControler = "tms.deleteAutoControler";
	public static final String getAllDcServerInAudit = "tms.getAllDcServerInAudit";
	public static final String getAllProjectorInAudit = "tms.getAllProjectorInAudit";
	public static final String getAllAudioInAudit = "tms.getAllAudioInAudit";
	public static final String getAllAutoConlInAudit = "tms.getAllAutoConlInAudit";
	public static final String getAudioInfo = "tms.getAudioInfo";
	public static final String getAuditoriumById = "tms.getAuditoriumById";
	public static final String getAutoConlInfo = "tms.getAutoConlInfo";
	public static final String getDcServerInfo = "tms.getDcServerInfo";
	public static final String getProjectorInfo = "tms.getProjectorInfo";
	
	public static final String getTMSCplDetail = "tms.getTMSCplDetail";
	public static final String deleteTMSCpl = "tms.deleteTMSCpl";
	public static final String getTMSAllCpl = "tms.getTMSAllCpl";
	public static final String deleteDcServerCpl = "tms.deleteDcServerCpl";
	public static final String deleteDcServerCplByScreen = "tms.deleteDcServerCplByScreen";
	public static final String setCplNameChn = "tms.setCplNameChn";
	public static final String importContentUsbLocalToTms = "tms.importContentUsbLocalToTms";
	public static final String importContentUsbLocalToTms2 = "tms.importContentUsbLocalToTms2";
	public static final String getTmsFreeSpaceSize = "tms.getTmsFreeSpaceSize";
	public static final String refreshTmsCpl = "tms.refreshTmsCpl";
	public static final String getImportStatus = "tms.getImportStatus";
	public static final String setLocalImportDefaultPath = "tms.setLocalImportDefaultPath";
	public static final String getLocalImportDefaultPath = "tms.getLocalImportDefaultPath";
	public static final String addImportFTPServer = "tms.addImportFTPServer";
	public static final String editImportFTPServer = "tms.editImportFTPServer";
	public static final String deleteImportFTPServer = "tms.deleteImportFTPServer";
	public static final String getImportFTPServer = "tms.getImportFTPServer";
	public static final String importContentFtpToTms = "tms.importContentFtpSatToTms";
	public static final String getImportSatellite = "tms.getImportSatellite";
	public static final String exportTMSContent = "tms.exportDcServerContent";
	public static final String cancelImportTask = "tms.cancelImportTask";
	public static final String cancelExportTask = "tms.cancelExportTask";
	public static final String stopTranTmsToDcServer = "tms.stopTranTmsToDcServer";
	public static final String resumeTranTmsToDcServer = "tms.resumeTranTmsToDcServer";
	public static final String retranTmsToDcServer = "tms.retranTmsToDcServer";
	public static final String setLocalExportDefaultPath = "tms.setLocalExportDefaultPath";
	public static final String getLocalExportDefaultPath = "tms.getLocalExportDefaultPath";
	public static final String cancelTranTmsToDcServer = "tms.cancelTranTmsToDcServer";
	public static final String getDcServerAllKdm = "tms.getDcServerAllKdm";
	public static final String getDcServerKdmDetail = "tms.getDcServerKdmDetail";
	public static final String getDcServerKdmsForCPL = "tms.getDcServerKdmsForCPL";
	public static final String deleteDcServerKdm = "tms.deleteDcServerKdm";
        public static final String deleteDcServerKdmByScreen = "tms.deleteDcServerKdmByScreen";
	public static final String deleteDcServerExpiredKdm = "tms.deleteDcServerExpiredKdm";
	public static final String importKdmToTms = "tms.importKdmToTms";
	public static final String getAllImportKdmToTms = "tms.getAllImportKdmToTms";
	public static final String setKdmLocalImportDefaultPath = "tms.setKdmLocalImportDefaultPath";
	public static final String getKdmLocalImportDefaultPath = "tms.getKdmLocalImportDefaultPath";
	public static final String transferTMSKdmToDcServer = "tms.transferTMSKdmToDcServer";
	public static final String getDcServInfoByCertThumbprint = "tms.getDcServInfoByCertThumbprint";
	public static final String getDcServInfoByPubThumbprint = "tms.getDcServInfoByPubThumbprint";
	
	public static final String getDcServerStatus = "tms.getDcServerStatus";
	public static final String getDcServerPlayModel = "tms.getDcServerPlayModel";
	public static final String getProjectorStatus = "tms.getProjectorStatus";
	public static final String getAudioProcStatus = "tms.getAudioProcStatus";
	public static final String getAutoControlerStatus = "tms.getAutoControlerStatus";
	public static final String getDcServerTodaySchedule = "tms.getDcServerTodaySchedule";
	public static final String setSchedule = "tms.setSchedule";
        public static final String setScheduleByScreen = "tms.setScheduleByScreen";
	public static final String deleteSchedule = "tms.deleteSchedule";
        public static final String deleteScheduleByScreen = "tms.deleteScheduleByScreen";
	public static final String addPosInfo = "tms.addPosInfo";

	public static final String setDcServerPlayModel = "tms.setDcServerPlayModel";
	public static final String setDcServerPlayModelByScreen = "tms.setDcServerPlayModelByScreen";
	public static final String setDcServerPlaySpl = "tms.setDcServerPlaySpl";
	public static final String setDcServerPlaySplByScreen = "tms.setDcServerPlaySplByScreen";
	public static final String setDcServerPauseSpl = "tms.setDcServerPauseSpl";
	public static final String setDcServerPauseSplByScreen = "tms.setDcServerPauseSplByScreen";
	public static final String setDcServerStopSpl = "tms.setDcServerStopSpl";
	public static final String setDcServerStopSplByScreen = "tms.setDcServerStopSplByScreen";
	public static final String setDcServerNextCpl = "tms.setDcServerNextCpl";
	public static final String setDcServerNextCplByScreen = "tms.setDcServerNextCplByScreen";
	public static final String setDcServerPreviousCpl = "tms.setDcServerPreviousCpl";
	public static final String setDcServerPreviousCplByScreen = "tms.setDcServerPreviousCplByScreen";
	public static final String setDcServerGotoPosition = "tms.setDcServerGotoPosition";
	public static final String setDcServerGotoPositionByScreen = "tms.setDcServerGotoPositionByScreen";
	public static final String setDcServerLoadSpl = "tms.setDcServerLoadSpl";
	public static final String setDcServerLoadCpl = "tms.setDcServerLoadCpl";
	public static final String setDcServerLoadSplByScreen = "tms.setDcServerLoadSplByScreen";
	public static final String setDcServerLoadCplByScreen = "tms.setDcServerLoadCplByScreen";
	public static final String getDcServerAllSpl = "tms.getDcServerAllSpl";
	public static final String getDcServerAllSplByScreen = "tms.getDcServerAllSplByScreen";
	public static final String getSplDetail = "tms.getSplDetail";
	public static final String setProjectorDouserModel = "tms.setProjectorDouserModel";
        public static final String setProjectorDouserModelByScreen = "tms.setProjectorDouserModelByScreen";
	public static final String setProjectorLampModel = "tms.setProjectorLampModel";
        public static final String setProjectorLampModelByScreen = "tms.setProjectorLampModelByScreen";
	public static final String setProjectorMarcoName = "tms.setProjectorMarcoName";
        public static final String setProjectorMarcoNameByScreen = "tms.setProjectorMarcoNameByScreen";
	public static final String getProjectorAllMarco = "tms.getProjectorAllMarco";
	public static final String getProjectorAllMarcoByScreen = "tms.getProjectorAllMarcoByScreen";
	public static final String setAudioProcVolume = "tms.setAudioProcVolume";
	public static final String setAudioProcVolumeByScreen = "tms.setAudioProcVolumeByScreen";
	public static final String setAudioProcChannelName = "tms.setAudioProcChannelName";
	public static final String setAudioProcChannelNameByScreen = "tms.setAudioProcChannelNameByScreen";
	public static final String getAudioProcAllChannel = "tms.getAudioProcAllChannel";
	public static final String getAudioProcAllChannelByScreen = "tms.getAudioProcAllChannelByScreen";
	public static final String setHouseLightModel = "tms.setHouseLightModel";
	public static final String setHouseLightModelByScreen = "tms.setHouseLightModelByScreen";
	public static final String getAudioProcVolume="tms.getAudioProcVolume";
	public static final String getAudioProcVolumeByScreen="tms.getAudioProcVolumeByScreen";
	public static final String getAudioProcVolumeScope="tms.getAudioProcVolumeScope";
	public static final String getAudioProcVolumeScopeByScreen="tms.getAudioProcVolumeScopeByScreen";

	public static final String getDcServerAllCpl = "tms.getDcServerAllCpl";
	public static final String getDcServerAllCplByScreen = "tms.getDcServerAllCplByScreen";
	public static final String getLmsAllCpl = "tms.getLmsAllCpl";
	public static final String getDcServerCplDetail = "tms.getDcServerCplDetail";
	
	public static final String setTheatreInfo = "tms.setTheatreInfo";
	public static final String getTheatreInfo = "tms.getTheatreInfo";
	public static final String getTmsFtpServer = "tms.getTmsFtpServer";
	public static final String setTmsFtpServer = "tms.setTmsFtpServer";
	public static final String getKdmStorPath = "tms.getKdmStorPath";
	public static final String setKdmStorPath = "tms.setKdmStorPath";
	public static final String setContentStorPath = "tms.setContentStorPath";
	public static final String getContentStorPath = "tms.getContentStorPath";
	public static final String setTmsUploadLogInfo = "tms.setTmsUploadLogInfo";
	public static final String getTmsUploadLogInfo = "tms.getTmsUploadLogInfo";
	public static final String setTmsSearchLogInfo = "tms.setTmsSearchLogInfo";
	public static final String getTmsSearchLogInfo = "tms.getTmsSearchLogInfo";
	public static final String setLmsInfo = "tms.setLmsInfo";
	public static final String getLmsInfo = "tms.getLmsInfo";
	public static final String getSatelliteServer = "tms.getSatelliteServer";
	public static final String setSatelliteServer = "tms.setSatelliteServer";
	public static final String setTmsSysName = "tms.setTmsSysName";
	public static final String getTmsSysName = "tms.getTmsSysName";
	public static final String setTmsSystemInfo = "tms.setTmsSystemInfo";
	public static final String getTmsSystemInfo = "tms.getTmsSystemInfo";
	public static final String setMonitorColumns = "tms.setMonitorColumns";
	public static final String getMonitorColumns = "tms.getMonitorColumns";
	public static final String setOperationLog = "tms.setOperationLog";
	public static final String getOperationLog = "tms.getOperationLog";
	public static final String setImportFTPServer = "tms.setImportFTPServer";
	
	public static final String getTransferStatus = "tms.getTransferStatus";
	public static final String getAllTransferUUID = "tms.getAllTransferUUID";
	public static final String getTranStaByCplUuid = "tms.getTranStaByCplUuid";
	public static final String getDcServerAllSplByName = "tms.getDcServerAllSplByName";
	public static final String getDcServerAllCue = "tms.getDcServerAllCue";
	public static final String getScreenAllCue = "tms.getScreenAllCue";
	public static final String addDcServerSpl = "tms.addDcServerSpl";
   public static final String addDcServerSplByScreenId = "tms.addDcServerSplByScreenId";
   public static final String addDcServerSplByScreenIds = "tms.addDcServerSplByScreenIds";
   public static final String addDcServerSplToScreenIds = "tms.addDcServerSplToScreenIds";
	public static final String setDcpAndKdmStorPath = "tms.setDcpAndKdmStorPath";
	public static final String getDcpAndKdmStorPath = "tms.getDcpAndKdmStorPath";
	public static final String getDcServerTime = "tms.getDcServerTime";
	public static final String getDcServerDiskInfo = "tms.getDcServerDiskInfo";
	public static final String getAllSchedule = "tms.getAllSchedule";
	public static final String getAuditSchedule = "tms.getAuditSchedule";
	public static final String getScheduleDetail = "tms.getScheduleDetail";
	public static final String transferTMSCplToDcServer="tms.transferTMSCplToDcServer";
	public static final String transferTMSCplToScreen="tms.transferTMSCplToScreen";
	public static final String deleteSpl="tms.deleteSpl";
        public static final String deleteSplByScreen="tms.deleteSplByScreen";
	public static final String IsHaveDcServerByIp="tms.IsHaveDcServerByIp";
	public static final String IsHaveProjectorByIp="tms.IsHaveProjectorByIp";
	public static final String IsHaveAudioByIp="tms.IsHaveAudioByIp";
	public static final String IsHaveAutoConlByIp="tms.IsHaveAutoConlByIp";
	public static final String setScreenOrderNo="tms.setScreenOrderNo";
}
