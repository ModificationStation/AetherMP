package net.mine_diver.aethermp.api.event;

public abstract class Event<T> {

    public T getInvoker() {
    	return invoker;
    }

    public abstract void register(T listener);
    protected T invoker;
}
