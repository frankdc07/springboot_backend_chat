package com.empresa.springboot.backend.chat.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.springboot.backend.chat.models.dao.ChatDao;
import com.empresa.springboot.backend.chat.models.documents.Mensaje;

@Service
public class ChatServiceImpl implements IChatService {
	
	@Autowired
	private ChatDao chatDao;
	
	@Override
	public List<Mensaje> obtenerUltimos10Mensajes() {
		return chatDao.findFirst10ByOrderByFechaDesc();
	}

	@Override
	public Mensaje guardar(Mensaje mensaje) {
		return chatDao.save(mensaje);
	}

}
