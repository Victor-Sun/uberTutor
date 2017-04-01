package com.gnomon.intergration.vault.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.jackrabbit.value.BinaryValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnomon.intergration.vault.VaultFile;
import com.gnomon.intergration.vault.VaultService;
import com.gnomon.intergration.vault.jcr.JcrService;
import com.gnomon.intergration.vault.jcr.JcrSessionCallback;
import com.gnomon.intergration.vault.jcr.WrapedSession;

@Service(value = "JcrVaultService")
@Transactional
public class JcrVaultService extends JcrService implements VaultService {
	public static final String DEFAULT_VAULT_WORKSPACENAME = "vault";

//	public static final String DOCUMENT_NODE_PROP_CREATEDBY = "jcr:createdBy";
//	public static final String DOCUMENT_NODE_PROP_CREATED = "jcr:created";
	
	public static final String CONTENT_NODE_NAME = "jcr:content";
	public static final String CONTENT_NODE_PROP_DATA = "jcr:data";

	public static final String DOCUMENT_NODE_NAME = "document";
	public static final String DOCUMENT_NODE_PROP_TITLE = "title";
//	public static final String INFO_NODE_PROP_DESCRIPTION = "description";
//	public static final String INFO_NODE_PROP_FILENAME = "filename";
//	public static final String INFO_NODE_PROP_CREATED_BY = "createdBy";
//	public static final String INFO_NODE_PROP_LASTMODIFIED_BY = "lastmodifiedBy";
	
	public static final String FILE_NODE_NAME = "file";

	public static final String SYS_NODE_TYPE_FOLDER = "nt:folder";
	public static final String SYS_NODE_TYPE_FILE = "nt:file";
	public static final String SYS_NODE_TYPE_RESOURCE = "nt:resource";
	
//	public static final String SYS_PROPERTY_TYPE_TITLE = "jcr:title";
//	public static final String SYS_PROPERTY_TYPE_DESCRIPTION = "jcr:description";
//	public static final String SYS_PROPERTY_TYPE_CREATED = "jcr:created";
//	public static final String SYS_PROPERTY_TYPE_LASTMODIFIED = "jcr:lastModified";

//	@Override
//	public String createFile(String workspaceName, final String filename, final byte[] content, final String userid)
//			throws Exception {
//		return createFile(workspaceName, filename, new ByteArrayInputStream(content), userid);
//	}

	@Override
	public String createFile(String workspaceName, final String filename, final InputStream is, final String userid)
			{
		return sessionTemplate.execute(workspaceName, new JcrSessionCallback<String>() {

			@Override
			public String doInSession(WrapedSession session) throws Exception {

				Node vaultNode = getVaultNode(session);
				
				//添加文件夹, 一个文件对应一个文件夹（名称使用UUID）
				Node documentFolder = vaultNode.addNode(UUID.randomUUID().toString());
				
				//添加文档节点
				Node documentNode = documentFolder.addNode(DOCUMENT_NODE_NAME);
				documentNode.setProperty(DOCUMENT_NODE_PROP_TITLE, filename);//文档标题（文件名称）
				
				//添加文件节点
				Node fileNode = documentNode.addNode(FILE_NODE_NAME, SYS_NODE_TYPE_FILE);
				
				//添加文件内容节点
				Node contentNode = fileNode.addNode(CONTENT_NODE_NAME, SYS_NODE_TYPE_RESOURCE);
				contentNode.setProperty(CONTENT_NODE_PROP_DATA, new BinaryValue(is));

				// 保存
				session.save();
				
				return documentNode.getIdentifier();
			}

		});

	}

	@Override
	public void updateFileContent(String workspaceName, final String fileId, final InputStream is, String userid){
		sessionTemplate.execute(workspaceName, new JcrSessionCallback<String>() {

			@Override
			public String doInSession(WrapedSession session) throws Exception {
				// find document node
				Node documentNode = session.getNodeByIdentifier(fileId);
				updateDocumentNodeContent(documentNode,is);
				// Save
				session.save();
				return null;
			}
		});
	}

	@Override
	public void getFileContent(String workspaceName, final String fileId, final OutputStream os){
		sessionTemplate.execute(workspaceName, new JcrSessionCallback<String>() {

			@Override
			public String doInSession(WrapedSession session) throws Exception {
				// find document node
				Node documentNode = session.getNodeByIdentifier(fileId);
				
				getDocumentNodeContent(documentNode, os);

				return null;
			}

		});

	}

	@Override
	public void deleteFile(String workspaceName, final String fileId) {
		sessionTemplate.execute(workspaceName, new JcrSessionCallback<String>() {

			@Override
			public String doInSession(WrapedSession session) throws Exception {
				// find document node
				Node documentNode = session.getNodeByIdentifier(fileId);

				//删除上级文件夹节点，级联删除下级的文件节点
				documentNode.getParent().remove();
//				//删除文件
//				documentNode.remove();
				
				// Save
				session.save();

				return null;
			}

		});
	}

//	@Override
//	public List<VaultFile> searchFile(String workspaceName, String filenameFilter) throws Exception {
//		
//		sessionTemplate.execute(workspaceName, new JcrSessionCallback<String>() {
//
//			@Override
//			public String doInSession(WrapedSession session) throws Exception {
//				// Obtain the query manager for the session ...
//
//				QueryManager queryManager = session.getWorkspace().getQueryManager();
//
//
//				// Create a query object ...
//
//				String language = "";
//
//				String expression = "";
//
//				Query query = queryManager.createQuery(expression,language);
//
//
//				// Execute the query and get the results ...
//
//				QueryResult result = query.execute();
//				// Iterate over the nodes in the results ...
//
//				javax.jcr.NodeIterator nodeIter = result.getNodes();
//
//				while ( nodeIter.hasNext() ) {
//
//				    javax.jcr.Node node = nodeIter.nextNode();
//
//				    //    ...
//
//				}
//				return null;
//			}
//
//		});
//		
//
//		
//		return null;
//	}

	private Node getVaultNode(WrapedSession session) throws RepositoryException {
		Node root = session.getRootNode();
		Node vaultNode = null;
		try{
			vaultNode = root.getNode("vault");
		}catch(PathNotFoundException e){
			vaultNode = root.addNode("vault");
		}
		return vaultNode;
	}

	private void getDocumentNodeContent(Node documentNode, final OutputStream os) throws PathNotFoundException,
			RepositoryException, ValueFormatException, IOException {
		Node fileNode = documentNode.getNode(FILE_NODE_NAME);
		Node contentNode = fileNode.getNode(CONTENT_NODE_NAME);
		Property property = contentNode.getProperty(CONTENT_NODE_PROP_DATA);
		Binary binary = property.getBinary();
		InputStream is = binary.getStream();
		while (is.available() > 0) {
			os.write(is.read());
		}
		is.close();
	}

	private void updateDocumentNodeContent(Node documentNode, final InputStream is) throws PathNotFoundException,
			RepositoryException, ValueFormatException, IOException {
		Node fileNode = documentNode.getNode(FILE_NODE_NAME);
		Node contentNode = fileNode.getNode(CONTENT_NODE_NAME);
		contentNode.setProperty(CONTENT_NODE_PROP_DATA, new BinaryValue(is));
	}

	@Override
	public VaultFile getFile(String workspaceName, final String fileId) {
		return sessionTemplate.execute(workspaceName, new JcrSessionCallback<VaultFile>() {

			@Override
			public VaultFile doInSession(WrapedSession session) throws Exception {
				// find document node
				Node documentNode = session.getNodeByIdentifier(fileId);
				Property title = documentNode.getProperty(DOCUMENT_NODE_PROP_TITLE);
				VaultFile vf = new VaultFile(documentNode.getIdentifier(),title.getString());
				return vf;
			}
		});
	}
}
