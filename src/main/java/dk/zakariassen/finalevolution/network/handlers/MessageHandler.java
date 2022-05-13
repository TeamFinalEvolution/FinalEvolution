package dk.zakariassen.finalevolution.network.handlers;

import dk.zakariassen.finalevolution.FinalEvolution;
import dk.zakariassen.finalevolution.network.messages.IMessage;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

abstract public class MessageHandler {
    private final HashMap<Class<?>, Method> handlers = new HashMap<>();

    public MessageHandler() {
        for (Method method : this.getClass().getDeclaredMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();

            if (parameterTypes.length != 2 || ! Modifier.isPublic(method.getModifiers())) {
                continue;
            }

            Class<?> firstParameterType = parameterTypes[0];

            if (IMessage.class.isAssignableFrom(firstParameterType)) {
                this.handlers.put(firstParameterType, method);
            }
        }
    }

    private Optional<Method> getHandler(IMessage message) {
        return Optional.ofNullable(this.handlers.get(message.getClass()));
    }

    public void handlePacket(IMessage message, Supplier<NetworkEvent.Context> context) {
        this.getHandler(message).ifPresentOrElse((Method method) -> {
            try {
                method.invoke(null, message, context);
            } catch (IllegalAccessException | InvocationTargetException e) {
                FinalEvolution.LOGGER.error(e);
            }
        }, () -> FinalEvolution.LOGGER.error(this.getClass().getName() + " -> Missing handler method for " + message.getClass().getName()));
    }
}
