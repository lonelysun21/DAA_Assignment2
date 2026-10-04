package benchmark;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PlotGenerator {

    private static class CsvRow {
        String workload;
        String variant;
        String structure;
        int n;
        double timeMs;
        long steps;
        long moves;
        long comparisons;

        CsvRow(String workload, String variant, String structure, int n,
               double timeMs, long steps, long moves, long comparisons) {
            this.workload = workload;
            this.variant = variant;
            this.structure = structure;
            this.n = n;
            this.timeMs = timeMs;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }

    public static void main(String[] args) {
        ArrayList<CsvRow> rows = readCsv();

        File plotsDirectory = new File("results/plots");

        if (!plotsDirectory.exists()) {
            plotsDirectory.mkdirs();
        }

        createTimeChart(rows, "W1", "-", "W1 Random Access", "w1_time");
        createOperationsChart(rows, "W1", "-", "W1 Random Access", "w1_operations");

        createTimeChart(rows, "W2", "-", "W2 Search", "w2_time");
        createOperationsChart(rows, "W2", "-", "W2 Search", "w2_operations");

        createTimeChart(rows, "W3", "head", "W3 Insert Remove Head", "w3_head_time");
        createOperationsChart(rows, "W3", "head", "W3 Insert Remove Head", "w3_head_operations");

        createTimeChart(rows, "W3", "middle", "W3 Insert Remove Middle", "w3_middle_time");
        createOperationsChart(rows, "W3", "middle", "W3 Insert Remove Middle", "w3_middle_operations");

        createTimeChart(rows, "W4", "-", "W4 Priority Processing", "w4_time");
        createOperationsChart(rows, "W4", "-", "W4 Priority Processing", "w4_operations");
    }

    private static ArrayList<CsvRow> readCsv() {
        ArrayList<CsvRow> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("results/results.csv"))) {

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                rows.add(new CsvRow(
                        parts[0],
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4]),
                        Long.parseLong(parts[5]),
                        Long.parseLong(parts[6]),
                        Long.parseLong(parts[7])
                ));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rows;
    }

    private static void createTimeChart(
            ArrayList<CsvRow> rows,
            String workload,
            String variant,
            String title,
            String fileName) {

        XYChart chart = new XYChartBuilder()
                .width(900)
                .height(600)
                .title(title + " - Time vs n")
                .xAxisTitle("n")
                .yAxisTitle("Time (ms)")
                .build();

        ArrayList<String> structures = getStructures(rows, workload, variant);

        for (String structure : structures) {
            ArrayList<Integer> nValues = new ArrayList<>();
            ArrayList<Double> times = new ArrayList<>();

            for (CsvRow row : rows) {
                if (row.workload.equals(workload)
                        && row.variant.equals(variant)
                        && row.structure.equals(structure)) {

                    nValues.add(row.n);
                    times.add(row.timeMs);
                }
            }

            chart.addSeries(structure, nValues, times);
        }

        saveChart(chart, fileName);
    }

    private static void createOperationsChart(
            ArrayList<CsvRow> rows,
            String workload,
            String variant,
            String title,
            String fileName) {

        XYChart chart = new XYChartBuilder()
                .width(900)
                .height(600)
                .title(title + " - Operations vs n")
                .xAxisTitle("n")
                .yAxisTitle("Operations")
                .build();

        ArrayList<String> structures = getStructures(rows, workload, variant);

        for (String structure : structures) {
            ArrayList<Integer> nValues = new ArrayList<>();
            ArrayList<Long> steps = new ArrayList<>();
            ArrayList<Long> moves = new ArrayList<>();
            ArrayList<Long> comparisons = new ArrayList<>();

            for (CsvRow row : rows) {
                if (row.workload.equals(workload)
                        && row.variant.equals(variant)
                        && row.structure.equals(structure)) {

                    nValues.add(row.n);
                    steps.add(row.steps);
                    moves.add(row.moves);
                    comparisons.add(row.comparisons);
                }
            }

            chart.addSeries(structure + " steps", nValues, steps);
            chart.addSeries(structure + " moves", nValues, moves);
            chart.addSeries(structure + " comparisons", nValues, comparisons);
        }

        saveChart(chart, fileName);
    }

    private static ArrayList<String> getStructures(
            ArrayList<CsvRow> rows,
            String workload,
            String variant) {

        ArrayList<String> structures = new ArrayList<>();

        for (CsvRow row : rows) {
            if (row.workload.equals(workload)
                    && row.variant.equals(variant)
                    && !structures.contains(row.structure)) {

                structures.add(row.structure);
            }
        }

        return structures;
    }

    private static void saveChart(XYChart chart, String fileName) {
        try {
            BitmapEncoder.saveBitmap(
                    chart,
                    "results/plots/" + fileName,
                    BitmapEncoder.BitmapFormat.PNG
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}