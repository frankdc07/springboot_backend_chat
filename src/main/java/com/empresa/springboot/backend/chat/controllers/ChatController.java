package com.empresa.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.empresa.springboot.backend.chat.models.documents.Mensaje;
import com.empresa.springboot.backend.chat.models.services.IChatService;

@Controller
public class ChatController {

	@Autowired
	private IChatService chatService;
	
	@Autowired
	private SimpMessagingTemplate webSocket;

	private String[] colores = { "red", "green", "blue", "magenta", "purple", "orange" };

	@MessageMapping("/mensaje")
	@SendTo("/chat/mensaje")
	public Mensaje recibeMensaje(Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		if (mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("Nuevo usuario conectado");
		}
		if (mensaje.getTipo().equals("DESCONECTADO")) {
			mensaje.setTexto("Usuario desconectado");
		}
		if (mensaje.getTipo().equals("MENSAJE")) {
			chatService.guardar(mensaje);
		}
		return mensaje;
	}

	@MessageMapping("/escribiendo")
	@SendTo("/chat/escribiendo")
	public String estaEscribiendo(Mensaje mensaje) {
		String response = "";
		if (!mensaje.getTexto().isBlank()) {
			response = mensaje.getUsername().concat(" está escribiendo ...");
		}
		return response;
	}

	@MessageMapping("/historial")
	public void consultarHistorial(String clienteId) {
		webSocket.convertAndSend("/chat/historial/" + clienteId, chatService.obtenerUltimos10Mensajes());
	}

}
