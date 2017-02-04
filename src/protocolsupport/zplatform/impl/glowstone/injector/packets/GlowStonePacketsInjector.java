package protocolsupport.zplatform.impl.glowstone.injector.packets;

import com.flowpowered.network.service.HandlerLookupService;

import net.glowstone.net.message.handshake.HandshakeMessage;
import net.glowstone.net.message.status.StatusPingMessage;
import net.glowstone.net.message.status.StatusRequestMessage;
import net.glowstone.net.protocol.GlowProtocol;
import net.glowstone.net.protocol.ProtocolType;
import protocolsupport.utils.ReflectionUtils;

public class GlowStonePacketsInjector {

	public static void inject() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		injectInboundHandler(ProtocolType.HANDSHAKE.getProtocol(), HandshakeMessage.class, GlowStoneHandshakeStartPacketHandler.class);
		injectInboundHandler(ProtocolType.STATUS.getProtocol(), StatusRequestMessage.class, GlowStoneStatusServerInfoRequestHandler.class);
		injectInboundHandler(ProtocolType.STATUS.getProtocol(), StatusPingMessage.class, GlowStoneStatusServerPingHandler.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void injectInboundHandler(GlowProtocol protocol, Class message, Class handler) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		HandlerLookupService svc = (HandlerLookupService) ReflectionUtils.getField(GlowProtocol.class, "handlers").get(protocol);
		svc.bind(message, handler);
	}

}
