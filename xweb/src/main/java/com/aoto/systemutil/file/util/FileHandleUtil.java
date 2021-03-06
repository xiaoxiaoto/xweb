package com.aoto.systemutil.file.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * @author Administrator
 *
 */
public class FileHandleUtil {

	/**
	 * 
	 * @author 赵德华 方法描述：< 上传文件到服务器>
	 * @param request
	 * @param serverFileName
	 * @param file
	 * @return 方法实现思路：<利用Spring文件上传控件实现文件上传 >
	 *
	 */
	public static Boolean uploadFile(HttpServletRequest request,String rootPath, String serverFileName,MultipartFile file){
		//设置上传目录
		if(request!=null&&rootPath != null && !rootPath.equals("")){
			 File directory = new File(rootPath);
		        if(!directory.exists()&&!directory.isDirectory()){
		        	directory.mkdir();
		        }
		        if(file!=null){
		        	 InputStream in =null;
		        	 FileOutputStream out =null;
		        	try {
		        	 String originalFilename = file.getOriginalFilename();//获取文件后缀
		        	 String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
		        	  in = file.getInputStream();//获取文件流
		        	  out = new FileOutputStream(rootPath+"\\"+serverFileName+suffix);//设置文件输出
		              //文件读写操作
		        	 int readLength = 0;
		        	 byte buffer[] = new byte[512];
		        	 while ((readLength = in.read(buffer, 0, buffer.length)) != -1) {
							out.write(buffer, 0, readLength);
						}
		        	out.flush();
		        	return true;
		        	}catch (Exception e) {
		        		return false;
					}finally{
						if (in != null) {
							try {
								in.close();
							} catch (IOException e) {
								return false;
							}
						}
						if (out != null) {
							try {
								out.close();
							} catch (IOException e) {
								return false;
							}
						}
					}
		        }
		}
       
       return false;
	}
	/**
	 * 
	 * @author 赵德华 方法描述：< 从服务器上下载文件>
	 * @param request
	 * @param response
	 * @param templetName
	 * @param serverTempletName
	 * @return 方法实现思路：<通过传入下载文件名称和服务器上的文件名下载文件 >
	 *
	 */
	public static Boolean downloadFile(HttpServletRequest request,
			HttpServletResponse response, String downloadName, String rootPath,
			String serverFileName) {
		if (request!=null&&serverFileName != null && !serverFileName.equals("")&&downloadName != null && !downloadName.equals("")&&rootPath != null && !rootPath.equals("")) {
			try {
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(
								downloadName.getBytes("gb2312"), "ISO8859-1"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				return false;
			} // 设置相应头
			response.setContentType("multipart/form-data");
			String serverFilePath = rootPath + "/"+serverFileName;// 模板路径

			ServletOutputStream out = null;
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(serverFilePath);
				out = response.getOutputStream();
				// 将内容写入输出流并把缓存的内容全部发出去
				int readLength = 0;
				byte[] buffer = new byte[512];
				while ((readLength = inputStream.read(buffer, 0,
						buffer.length)) != -1) {
					// 4.写到输出流(out)中
					out.write(buffer, 0, readLength);
				}
				out.flush();
				return true;
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						return false;
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						return false;
					}
				}
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @author 赵德华 方法描述：< 从excel导入数据>
	 * @param xmlPath
	 * @param excelFile
	 * @param readKey
	 * @param rootPath
	 * @return 方法实现思路：<从excel导入数据 >
	 *
	 */
	 public static List<?> importExcel4JXLS(String xmlPath,MultipartFile excelFile,List<?> data,String readKey,String rootPath){
		 if(xmlPath!=null&&!xmlPath.equals("")&&excelFile!=null&&data!=null&&readKey!=null&&!readKey.equals("")&&rootPath!=null&&!rootPath.equals("")){
			 InputStream inputXML=null;
			 XLSReader mainReader =null;
			 InputStream inputXLS =null;
			try {
				inputXML = new BufferedInputStream(new FileInputStream(new File(rootPath+xmlPath)));
			    mainReader = ReaderBuilder.buildFromXML(inputXML);
			    
			    inputXLS = excelFile.getInputStream();
				Map<String,List<?>> beans = new HashMap<String,List<?>>();
				beans.put(readKey, data);
				mainReader.read(inputXLS, beans);
				return data;
			} catch (IOException | SAXException | org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
				e.printStackTrace();
				return data;
			} finally {
				if (inputXML != null) {
					try {
						inputXML.close();
					} catch (IOException e) {
						return data;
					}
				}
				if (inputXLS != null) {
					try {
						inputXLS.close();
					} catch (IOException e) {
						return data;
					}
				}
			}
		 }
		return data;
	 }
	 
	 /**
		 * 
		 * @author 赵德华 方法描述：< 使用JXLS导出excel>
		 * @param response
		 * @param templet
		 * @param newExeclName
		 * @param exportData
		 * @return 方法实现思路：<使用JXLS导出excel >
		 *
		 */
	public static Boolean exportExcel4JXLS(HttpServletResponse response,
			String templet, String newExeclName,
			Map<String, Object> exportData) {
		XLSTransformer former = new XLSTransformer();
		if (templet != null &&!templet.equals("")&& exportData != null&&newExeclName!=null&&!newExeclName.equals("")&&response!=null) {
			InputStream in = null;
			OutputStream out = null;
			// 设置响应
			try {
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(
								newExeclName.getBytes("gb2312"), "ISO8859-1"));
				response.setContentType("application/vnd.ms-excel");
				in = new BufferedInputStream(new FileInputStream(templet));// 读取Excel模板
				Workbook workbook = former.transformXLS(in, exportData);// 使用JXLS生成Excel工作簿
				out = response.getOutputStream();
				// 将内容写入输出流并把缓存的内容全部发出去
				workbook.write(out);
				out.flush();
				return true;
			} catch (ParsePropertyException
					| org.apache.poi.openxml4j.exceptions.InvalidFormatException
					| IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						return false;
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						return false;
					}
				}
			}
		}
		return false;
	}

}
