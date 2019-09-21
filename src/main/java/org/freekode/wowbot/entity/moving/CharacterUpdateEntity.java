package org.freekode.wowbot.entity.moving;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class CharacterUpdateEntity {

	private String state = "";

	private Date date;

	private Vector3D coordinates;

	private Action action;

	public CharacterUpdateEntity(Vector3D coordinates) {
		this.coordinates = coordinates;
	}

	public CharacterUpdateEntity(Date date, Vector3D coordinates, Action action) {
		this.date = date;
		this.coordinates = coordinates;
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public Vector3D getCoordinates() {
		return coordinates;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Action getAction() {
		return action;
	}

	public List<Object> toList() {
		List<Object> list = new LinkedList<>();
		list.add(state);
		list.add(date);
		list.add(coordinates.getX());
		list.add(coordinates.getY());
		list.add(action);

		return list;
	}

	@Override
	public String toString() {
		return "Record[" + coordinates.getX() + "; " + coordinates.getY() + "; " + action + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CharacterUpdateEntity that = (CharacterUpdateEntity) o;

		return coordinates.equals(that.coordinates);

	}

	@Override
	public int hashCode() {
		return coordinates.hashCode();
	}

	public enum Action {
		MOVE,
		GATHER
	}
}
