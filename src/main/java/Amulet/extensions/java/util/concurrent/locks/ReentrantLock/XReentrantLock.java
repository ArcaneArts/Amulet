package Amulet.extensions.java.util.concurrent.locks.ReentrantLock;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import java.util.concurrent.locks.ReentrantLock;

@Extension
public class XReentrantLock {
  public static void helloWorld(@This ReentrantLock thiz) {
    System.out.println("hello world!");
  }
}