package com.example.BounceOrLose;

/**
 * Created by bernardot on 2/10/16.
 */
public class Wall {
    private Vector2D startPosition, endPosition, tangentVector, normalVector;
    private double length;
    private Double depth;
    private int color;

    public Wall(double sx, double sy, double ex, double ey, int col) {
        // Setting depth to null. This means barrier is infinite.
        this(sx, sy, ex, ey, col, null);
    }

    public Wall(double sx, double sy, double ex, double ey, int col, Double d) {
        startPosition = new Vector2D(sx, sy);
        endPosition = new Vector2D(ex, ey);
        color = col;
        depth = d;

        // Calculate wall length
        Vector2D lengthVector = Vector2D.minus(endPosition, startPosition);
        length = lengthVector.magnitude();

        // Obtain tangent vector
        tangentVector = lengthVector;
        tangentVector.normalise();

        // Obtain normal vector;
        normalVector = tangentVector.rotateVector90degreesAnticlockwise();
    }

    public boolean isBallCollidingWall(Vector2D ballCentre, double ballRadius) {
        Vector2D vectorFromStartPosToCircleCentre = Vector2D.minus(ballCentre, startPosition);
        double circleDistanceFromWall = vectorFromStartPosToCircleCentre.scalarProduct(normalVector);
        double circleDistanceAlongWall = vectorFromStartPosToCircleCentre.scalarProduct(tangentVector);

        return circleDistanceFromWall <= ballRadius && (depth == null || circleDistanceFromWall >= -(depth + ballRadius))
                && circleDistanceAlongWall >= 0 && circleDistanceAlongWall <= length;
    }

    public Vector2D calculateVelocityAfterACollision(Vector2D position, Vector2D velocity) {
        double vParallel = velocity.scalarProduct(tangentVector);
        double vNormal = velocity.scalarProduct(normalVector);
        if (vNormal < 0) // assumes normal points AWAY from wall...
            vNormal = -vNormal;
        Vector2D result = tangentVector;
        result.multiplyScalar(vParallel);
        result.addScaled(normalVector, vNormal);
        return result;
    }
}
