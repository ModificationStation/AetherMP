package net.mine_diver.aetherapi.event;

public abstract class Event<T> {

    public T getInvoker() {
    	return invoker;
    }

    public abstract void register(T listener);
    protected T invoker;
}
