public class Main {
    public static void main(String[] args) {
        HumanBMI humanBMI = new HumanBMI(80, 1.52);
        System.out.println(humanBMI.getResult());
    }
}

class HumanBMI {
    private double weight;
    private double height;

    public HumanBMI(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double calculateBMI() {
        return weight / (height * height);
    }

    public String getResult() {
        double bmi = calculateBMI();
        if (bmi < 18.5) {
            return "Deficit";
        } else if (bmi < 25) {
            return "Norm";
        } else if (bmi < 30) {
            return "Warning!";
        } else {
            return "Fat";
        }
    }
}