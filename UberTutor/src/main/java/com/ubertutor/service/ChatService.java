package com.ubertutor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ubertutor.dao.ChatDAO;
import com.ubertutor.entity.ChatEntity;

@Service
@Transactional
public class ChatService {

		@Autowired
		private ChatDAO chatDAO;
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		/**
		 * Saves entity to db
		 * @param ChatEntity entity
		 */
		public void save(ChatEntity entity){
			this.chatDAO.save(entity);
		}
		
		public Map<String, Object> getMessageList(int userId){
			List<Object> params = new ArrayList<Object>();
			String sql = "SELECT * FROM CHAT WHERE FROM_USER_ID = ? OR TO_USER_ID = ? SORT BY FROM_USER_ID";
			params.add(userId);
			params.add(userId);
			return this.jdbcTemplate.queryForMap(sql, params.toArray());
		}
		
		public Map<String, Object> getMessageLog(int fromUserId, int toUserId){
			List<Object> params = new ArrayList<Object>();
			String sql = "SELECT * FROM CHAT WHERE FROM_USER_ID = ? AND TO_USER_ID = ?";
			params.add(fromUserId);
			params.add(toUserId);
			return this.jdbcTemplate.queryForMap(sql, params.toArray());
		}
}
