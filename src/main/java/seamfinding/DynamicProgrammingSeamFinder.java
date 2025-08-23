package seamfinding;

import seamfinding.energy.EnergyFunction;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        // Make table with the energy values for each pixel
        double[][] energyTable = fillEnergyTable(picture, f);
        // Backtrack through the table to find the seam and return it
        return findMinSeam(energyTable, picture.width(), picture.height());
    }

    private double[][] fillEnergyTable(Picture picture, EnergyFunction f) {
        double[][] energyTable = new double[picture.width()][picture.height()];

        // Case 1: Process energy values in first column
        for (int y = 0; y < picture.height(); y += 1) {
            energyTable[0][y] = f.apply(picture, 0, y);
        }
        // Case 2: Process energy values for subsequent columns and back calculate
        for (int x = 1; x < picture.width(); x += 1) {
            for (int y = 0; y < picture.height(); y += 1) {
                double pixelEnergy = f.apply(picture, x, y);

                double minPixelEnergy = energyTable[x - 1][y];

                // Base Case: Checking top left neighbor
                if (y > 0) {
                    minPixelEnergy = Math.min(minPixelEnergy, energyTable[x - 1][y - 1]);
                }
                // Base Case: Checking bottom left neighbor
                if (y < picture.height() - 1) {
                    minPixelEnergy = Math.min(minPixelEnergy, energyTable[x - 1][y + 1]);
                }
                // New value is its energy plus the minPixelEnergy of the previous pixels
                energyTable[x][y] = pixelEnergy + minPixelEnergy;
            }
        }
        return energyTable;
    }

    private List<Integer> findMinSeam(double[][] energyTable, int width, int height) {
        // seam is the list of y coords for each column to make the path
        // of least sig. pixels
        List<Integer> seam = new ArrayList<>();

        // Set the starting point for the backtracking
        double minEnergy = Double.POSITIVE_INFINITY;
        int y_minEnergy = 0;
        // Iterate through and locate the min
        for (int y = 0; y < height; y++) {
            if (energyTable[width - 1][y] < minEnergy) {
                minEnergy = energyTable[width - 1][y];
                y_minEnergy = y;
            }
        }
        // Add the start point
        seam.add(y_minEnergy);

        // Backtrack to find the rest of the seam
        int curr_Y = y_minEnergy;
        for (int x = width - 2; x >= 0; x--) {
            int prev_Y = curr_Y;
            double minPrevEnergy = energyTable[x][curr_Y];

            // Check if within upper bound and if the previous above pixel is less significant
            if (curr_Y > 0 && energyTable[x][curr_Y - 1] < minPrevEnergy) {
                minPrevEnergy = energyTable[x][curr_Y - 1];
                prev_Y = curr_Y - 1;
            }
            // Check if within lower bound and if the previous below pixel is less significant
            if (curr_Y < height - 1 && energyTable[x][curr_Y + 1] < minPrevEnergy) {
                prev_Y = curr_Y + 1;
            }
            // Add the best previous pixel's y coord to the seam list
            seam.add(prev_Y);
            curr_Y = prev_Y; // Update new previous pixel y coord
        }
        // Since the seam is done in reverse, restore order by reversing
        Collections.reverse(seam);
        return seam;
    }
}

