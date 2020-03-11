package br.com.zup.order.event;

public class Message<T> {

    private T event;

    public Message() {
    }

    public Message(T event) {
        this.event = event;
    }

    public T getEvent() {
        return event;
    }

    public void setEvent(T event) {
        this.event = event;
    }
}
