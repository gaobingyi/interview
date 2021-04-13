package com.gby.pattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Movable movable = new MovableProxy(new Car()).getMovable();
        movable.move();
    }
}

class MovableProxy implements InvocationHandler {
    private Movable movable;

    public MovableProxy(Movable movable) {
        this.movable = movable;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // pre
        Object result = method.invoke(movable, args);
        // post
        return result;
    }

    public Movable getMovable() {
        return (Movable) Proxy.newProxyInstance(movable.getClass().getClassLoader(), movable.getClass().getInterfaces(), this);
    }
}

interface Movable {
    void move();
}

class Car implements Movable {
    @Override
    public void move() {

    }
}
