package interview;

// First attempt to store information as a pair. The problem here is a start city can be the second
// value, end city can be on first place. Leaved here for future enhancements.

public class CityPair<T, U> {

	/**
	 * (C) 2019
	 * 
	 * @author eskvaznikov
	 *
	 * 
	 */
	public final T key;
	public final U value;

	public CityPair(T key, U value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityPair<?, ?> other = (CityPair<?, ?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
