package com.empresa.springboot.backend.chat.models.services;

import java.util.List;

import com.empresa.springboot.backend.chat.models.documents.Mensaje;

public interface IChatService {
	
	public List<Mensaje> obtenerUltimos10Mensajes();
	
	public Mensaje guardar(Mensaje mensaje);
	
}
