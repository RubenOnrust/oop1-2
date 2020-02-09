package util.collection;

import java.util.ArrayList;
import java.util.List;

import util.IllegalParameterException;

@SuppressWarnings("unchecked")
public class Tree<T> {
	private T root;
	private List<TreeElement> elements;
	
	public Tree(T root) {
		this.root = root;
		elements = new ArrayList<>();
		elements.add(new TreeElement(root, null));
	}
	
	public Tree(T root, int size) {
		this.root = root;
		elements = new ArrayList<>(size);
		elements.add(new TreeElement(root, null));
	}
	
	public T getRoot() {
		return this.root;
	}
	
	public List<T> getChildren(T element) {
		return this.getElement(element).getChildren();
	}
	
	public T getParent(T element) {
		return this.getElement(element).getParent();
	}
	
	public List<T> getDescendants(T element) {
		List<T> result = new ArrayList<>();
		List<T> children = this.getElement(element).getChildren();
		
		// Add the children found to the toRemove list
		result.addAll(children);
		
		// Add all descendants of the children as well
		for (T child: children) {
			result.addAll(this.getDescendants(child));
		}
		return result;
	}
	
	public boolean addElement(T parent, T child) {
		TreeElement p = new TreeElement(parent, null);
		if (elements.contains(p)) {
			TreeElement c = new TreeElement(child, null);
			if (elements.contains(c)) {
				throw new IllegalStateException("Element already in DAG");
			}
			else {
				elements.add(c);
				return this.getElement(p).addChild(child);
			}
		}
		else {
			throw new IllegalParameterException("Parent unknown in DAG");
		}
	}

	public void deleteElement(T element) {
		// Make sure the root is never deleted
		if (element.equals(root) ) {
			throw new IllegalParameterException("Root cannot be deleted from the DAG");
		}
		
		TreeElement p = new TreeElement(element, null);
		if (elements.contains(p)) {
			// Collect all descendants of the node to be removed
			List<TreeElement> descendants = this.getTreeDescendants(element);
	
			// Remove the node as a child from its parent
			TreeElement parent = this.getElement(p.getParent());
			parent.deleteChild(element);
			
			// Remove the descendants from the list of elements
			elements.removeAll(descendants);
		}
		else {
			throw new IllegalParameterException("Element unknown in DAG");
		}
	}
	
	private List<TreeElement> getTreeDescendants(T element) {
		List<T> elements = this.getDescendants(element);
		List<TreeElement> result = new ArrayList<>(elements.size());
		for (T content: elements) {
			result.add(this.getElement(content));
		}
		return result;
	}
	
	// Returns the object in the internal tree which is equal() to the parameter provided
	private TreeElement getElement(TreeElement p) {
		for (TreeElement element: elements) {
			if (element.equals(p)) {
				return element;
			}
		}
		throw new IllegalStateException("Unrecognized internal DAG state");
	}
	
	// Returns the object in the internal tree which is equal() to the parameter provided
	private TreeElement getElement(T p) {
		return this.getElement(new TreeElement(p, null));
	}
	
	public T get(int index) {
		return elements.get(index).getElement();
	}

	private class TreeElement {
		private T element;
		private T parent;
		private List<T> children;
		
		private TreeElement(T element, T parent) {
			this.element = element;
			this.parent = parent;
		}
		
		public T getElement() {
			return this.element;
		}

		private boolean addChild(T child) {
			return children.add(child);
		}
		
		private boolean deleteChild(T child) {
			return children.remove(child);
		}
		
		private T getParent() {
			return this.parent;
		}
		
		private List<T> getChildren() {
			return this.children;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((element == null) ? 0 : element.hashCode());
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
			TreeElement other = (TreeElement) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (element == null) {
				if (other.element != null)
					return false;
			} else if (!element.equals(other.element))
				return false;
			return true;
		}

		private Tree<T> getEnclosingInstance() {
			return Tree.this;
		}
	}
}
