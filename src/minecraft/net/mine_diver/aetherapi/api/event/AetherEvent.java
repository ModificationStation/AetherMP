package net.mine_diver.aetherapi.api.event;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Function;

public class AetherEvent<T> extends Event<T> {

	private T[] handlers;
	private final Class<T> type;
	private final Function<T[], T> eventFunc;
	public AetherEvent(Class<T> type, Function<T[], T> eventFunc) {
		this.type = type;
		this.eventFunc = eventFunc;
		update();
		EVENTS = Arrays.copyOf(EVENTS, EVENTS.length + 1);
		EVENTS[EVENTS.length - 1] = this;
	}
	
	public Class<T> getType() {
	    return type;
	}

	@SuppressWarnings("unchecked")
	void update() {
		if (handlers == null)
			invoker = eventFunc.apply((T[]) Array.newInstance(type, 0));
		else if (handlers.length == 1)
			invoker = handlers[0];
		else
			invoker = eventFunc.apply(handlers);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void register(T listener) {
		if (handlers == null) {
			handlers = (T[]) Array.newInstance(type, 1);
			handlers[0] = listener;
		}
		else {
			handlers = Arrays.copyOf(handlers, handlers.length + 1);
			handlers[handlers.length - 1] = listener;
		}
  		update();
	}

    public static AetherEvent<?>[] EVENTS = new AetherEvent[0];
}
