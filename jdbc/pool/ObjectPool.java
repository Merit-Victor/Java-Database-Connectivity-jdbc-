package eg.edu.alexu.csd.oop.jdbc.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Merit Victor
 *
 * @param <T>
 */
public abstract class ObjectPool<T> {
	
	/**
	 * 
	 */
	private Queue<T> connections;
		

	/**
	 * @param t
	 */
	public void checkIn(T t) {
		if (t == null) {
			return;
		}
		this.connections.offer(t);
	}
	
	/**
	 * @return
	 */
	public T checkOut() {
		T t;
		t = connections.poll();
		return (t);
	}

	/**
	 * @return
	 */
	protected abstract T create();
	

	/**
	 * @param minObjects
	 */
	protected void initialize(final int minObjects) {
		connections = new ConcurrentLinkedQueue<T>();
		for (int i = 0; i < minObjects; i++) {
			connections.add(create());
		}
	}
}
