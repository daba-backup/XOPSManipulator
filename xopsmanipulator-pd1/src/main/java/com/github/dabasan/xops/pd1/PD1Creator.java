package com.github.dabasan.xops.pd1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * Creates PD1 points.
 * 
 * @author Daba
 *
 */
public class PD1Creator {
	private final Logger logger = LoggerFactory.getLogger(PD1Creator.class);

	private int point_count;
	private final Map<Integer, PD1Point> points_map;

	public PD1Creator() {
		point_count = 0;
		points_map = new HashMap<>();
	}

	/**
	 * Returns all point handles.
	 * 
	 * @return Point handles
	 */
	public Set<Integer> GetPointHandles() {
		return new HashSet<>(points_map.keySet());
	}

	/**
	 * Adds a point to the map.
	 * 
	 * @param point
	 *            Point
	 * @return Point handle
	 */
	public int AddPoint(PD1Point point) {
		if (point == null) {
			logger.warn("Null argument where non-null required.");
			return -1;
		}

		final int point_handle = point_count;
		point_count++;

		points_map.put(point_handle, point);

		return point_handle;
	}

	/**
	 * Duplicates a point.
	 * 
	 * @param point_handle
	 *            Handle of the point to be duplicated
	 * @return Point handle
	 */
	public int DuplicatePoint(int point_handle) {
		if (points_map.containsKey(point_handle) == false) {
			logger.warn("No such point. point_handle={}", point_handle);
			return -1;
		}

		final PD1Point orig_point = points_map.get(point_handle);
		final PD1Point duplicated_point = new PD1Point(orig_point);

		final int duplicated_point_handle = point_count;
		point_count++;

		points_map.put(duplicated_point_handle, duplicated_point);

		return point_handle;
	}

	/**
	 * Creates a point.
	 * 
	 * @param position
	 *            Position
	 * @param rotation
	 *            Rotation (radian)
	 * @param parameter_1
	 *            Parameter 1
	 * @param parameter_2
	 *            Parameter 2
	 * @param parameter_3
	 *            Parameter 3
	 * @param parameter_4
	 *            Parameter 4
	 * @return Point handle
	 */
	public int CreatePoint(Vector position, float rotation, int parameter_1,
			int parameter_2, int parameter_3, int parameter_4) {
		final PD1Point point = new PD1Point();

		point.SetPosition(position);
		point.SetRotation(rotation);
		point.SetParameter(0, parameter_1);
		point.SetParameter(1, parameter_2);
		point.SetParameter(2, parameter_3);
		point.SetParameter(3, parameter_4);

		final int point_handle = point_count;
		point_count++;

		points_map.put(point_handle, point);

		return point_handle;
	}

	/**
	 * Sets the position of a point.
	 * 
	 * @param point_handle
	 *            Handle of the point
	 * @param position
	 *            Position
	 * @return -1 on error and 0 on success
	 */
	public int SetPointPosition(int point_handle, Vector position) {
		if (points_map.containsKey(point_handle) == false) {
			logger.warn("No such point. point_handle={}", point_handle);
			return -1;
		}

		final PD1Point point = points_map.get(point_handle);
		point.SetPosition(position);

		return 0;
	}
	/**
	 * Sets the rotation of a point.
	 * 
	 * @param point_handle
	 *            Handle of the point
	 * @param rotation
	 *            Rotation (radian)
	 * @return -1 on error and 0 on success
	 */
	public int SetPointRotation(int point_handle, float rotation) {
		if (points_map.containsKey(point_handle) == false) {
			logger.warn("No such point. point_handle={}", point_handle);
			return -1;
		}

		final PD1Point point = points_map.get(point_handle);
		point.SetRotation(rotation);

		return 0;
	}
	/**
	 * Sets the parameters of a point.
	 * 
	 * @param point_handle
	 *            Handle of the point
	 * @param parameter_1
	 *            Parameter 1
	 * @param parameter_2
	 *            Parameter 2
	 * @param parameter_3
	 *            Parameter 3
	 * @param parameter_4
	 *            Parameter 4
	 * @return -1 on error and 0 on success
	 */
	public int SetPointParameters(int point_handle, int parameter_1,
			int parameter_2, int parameter_3, int parameter_4) {
		if (points_map.containsKey(point_handle) == false) {
			logger.warn("No such point. point_handle={}", point_handle);
			return -1;
		}

		final PD1Point point = points_map.get(point_handle);
		point.SetParameter(0, parameter_1);
		point.SetParameter(1, parameter_2);
		point.SetParameter(2, parameter_3);
		point.SetParameter(3, parameter_4);

		return 0;
	}

	/**
	 * Removes a point.
	 * 
	 * @param point_handle
	 *            Point handle
	 * @return -1 on error and 0 on success
	 */
	public int RemovePoint(int point_handle) {
		if (points_map.containsKey(point_handle) == false) {
			logger.warn("No such point. point_handle={}", point_handle);
			return -1;
		}

		points_map.remove(point_handle);

		return 0;
	}

	/**
	 * Writes out points into a PD1 file.
	 * 
	 * @param pd1_filename
	 *            PD1 filename
	 * @param offset_y
	 *            Y-direction offset
	 * @return -1 on error and 0 on success
	 */
	public int Write(String pd1_filename, float offset_y) {
		final PD1Manipulator pd1_manipulator = new PD1Manipulator();

		final List<PD1Point> points = new ArrayList<>();
		for (final PD1Point point : points_map.values()) {
			points.add(point);
		}

		pd1_manipulator.SetPoints(points);
		pd1_manipulator.Translate(VectorFunctions.VGet(0.0f, offset_y, 0.0f));

		final int ret = pd1_manipulator.Write(pd1_filename);

		return ret;
	}
}
