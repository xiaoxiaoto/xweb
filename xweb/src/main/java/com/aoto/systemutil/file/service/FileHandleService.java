package com.aoto.systemutil.file.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aoto.systemutil.domain.repository.pojo.Domain;
import com.aoto.systemutil.domain.service.DomainService;
import com.aoto.systemutil.file.util.FileHandleUtil;

@Service
public class FileHandleService {
	@Value("${file.handle.upload.rootpath}")
	private String uploadRootpath;
	@Value("${file.handle.servicer.resource}")
	private String servicerResource;
	@Value("${file.handle.excl.import.rootpath}")
	private String importTempletRootpath;
	@Value("${file.handle.excl.export.rootpath}")
	private String exportTempletRootPath;

	@Autowired
	private DomainService domainService;

	public Map<String, Object> handleFileUpload(HttpServletRequest request,
			MultipartFile file) {
		Map<String, Object> result = new HashMap<>();
		if(uploadRootpath==null || uploadRootpath.equals("")){
			uploadRootpath="C:/FileUpload";
		}
		String serverFileName = "文件模板";
		Boolean uploadFile = FileHandleUtil.uploadFile(request, uploadRootpath,
				serverFileName, file);
		if(uploadFile){
			result.put("result", "上传成功！");
		}else{
			result.put("result", "上传失败！");
		}
		return null;
	}
	
	public Map<String, Object> handleFileDownload(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		if(servicerResource==null || servicerResource.equals("")){
			servicerResource="C:/MMSFiles/ServicerResource/";
		}
		String downloadName = "文件下载示例.js";
		String serverFileName = "文件模板.js";
		Boolean downloadFile = FileHandleUtil.downloadFile(request, response,
				downloadName, servicerResource, serverFileName);
		if (downloadFile) {
			result.put("result", "下载成功！");
		} else {
			result.put("result", "下载失败！");
		}
		return result;
	}

	public Map<String, Object> exportData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		if(exportTempletRootPath==null || exportTempletRootPath.equals("")){
			exportTempletRootPath="C:/ExportTemplet";
		}
		String templet =exportTempletRootPath+ "Templet\\dept.xlsx";
		String newExeclName = "导出测试.xlsx";
		Map<String, Object> exportData = new HashMap<>();
		String[] titleArray = new String[]{"编号", "值", "文本"};
		List<Domain> domains = domainService.queryAllDmain();
		exportData.put("titles", titleArray);
		exportData.put("domains", domains);
		Boolean exportExecl4JXLS = FileHandleUtil.exportExcel4JXLS(response, templet, newExeclName,
				exportData);
		if(exportExecl4JXLS){
			result.put("result", "导出成功！");
		}else{
			result.put("result", "导出失败！");
		}
		return null;
	}
	
	
	public Map<String, Object> importData(HttpServletRequest request,
			HttpServletResponse response,MultipartFile file) {
		Map<String, Object> result = new HashMap<>();
		if(importTempletRootpath==null || importTempletRootpath.equals("")){
			importTempletRootpath="C:/ExportTemplet";
		}
		List<Domain> data=new ArrayList<>();
		String xmlPath="Templet/domain.xml";
		List<?> list = FileHandleUtil.importExcel4JXLS(xmlPath, file, data, "domains", importTempletRootpath);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Domain object = (Domain)list.get(i);
				String text = object.getDomainValue();
				System.out.println(text);
			}
			result.put("result", "导入成功！");
		}else{
			result.put("result", "导入失败！");
		}
		return null;
	}

}
