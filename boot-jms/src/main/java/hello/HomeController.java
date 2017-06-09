package hello;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.ws.Data;

@Controller
public class HomeController {
	@Autowired
	private SimpMessagingTemplate webSocket;
	@Autowired
	SessionRegistry sr;

	@MessageMapping("/topic/hello")
	public void greeting(Data data, Principal p) throws Exception {
		List<Object> allPrincipals = sr.getAllPrincipals();
		data.setUsers(allPrincipals.size());
		data.setLogin(p.getName());
		data.setRespo(data.getName() + p.getName() + " " + allPrincipals.size());
		webSocket.convertAndSendToUser(p.getName(), "/queue/priv", data);
	}

	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/ws")
	public String ws() {
		return "ws";
	}

}
