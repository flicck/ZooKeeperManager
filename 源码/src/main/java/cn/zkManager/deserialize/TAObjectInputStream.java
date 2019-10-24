package cn.zkManager.deserialize;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Proxy;

/**
 * An ObjectInputStream that is aware of the TCCL.
 */
public class TAObjectInputStream extends ObjectInputStream {

	public TAObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	/**
	 * Load the local class equivalent of the specified stream class
	 * description.
	 * Uses the current class {@link ClassLoader} and falls back to the {@link Thread} context {@link ClassLoader}.
	 */
	protected Class resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
		try {
			return getClass().getClassLoader().loadClass(classDesc.getName());
		} catch (ClassNotFoundException ex) {
			ClassLoader tccl = Thread.currentThread().getContextClassLoader();
			if (tccl != null) {
				return tccl.loadClass(classDesc.getName());
			} else {
				throw ex;
			}
		}
	}

	/**
	 * Returns a proxy class that implements the interfaces named in a proxy
	 * class descriptor; subclasses may implement this method to read custom
	 * data from the stream along with the descriptors for dynamic proxy
	 * classes, allowing them to use an alternate loading mechanism for the
	 * interfaces and the proxy class.
	 *
	 * For each interface uses the current class {@link ClassLoader} and falls back to the {@link Thread} context {@link ClassLoader}.
	 */
	protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
		ClassLoader cl = getClass().getClassLoader();
		Class[] cinterfaces = new Class[interfaces.length];

		for (int i = 0; i < interfaces.length; i++) {
			try {
				cinterfaces[i] = cl.loadClass(interfaces[i]);
			} catch (ClassNotFoundException ex) {
				ClassLoader tccl = Thread.currentThread().getContextClassLoader();
				if (tccl != null) {
					return tccl.loadClass(interfaces[i]);
				} else {
					throw ex;
				}
			}
		}
		try {
			return Proxy.getProxyClass(cinterfaces[0].getClassLoader(), cinterfaces);
		} catch (IllegalArgumentException e) {
			throw new ClassNotFoundException(null, e);
		}
	}
}