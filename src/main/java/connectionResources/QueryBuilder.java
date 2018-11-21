import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class QueryBuilder {
    public QueryInfo select(String table) {
        return new QueryInfo(String.format("SELECT * FROM `%s`", table), null);
    }

    public QueryInfo select(String[] columns, String table) {
        return select(columns, table, null);
    }

    public QueryInfo select(String[] columns, String table, String leftWhereOperand) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        if (columns == null || columns.length < 1)
            return null;


        IntStream.range(0, columns.length)
                .forEach(i -> {
                    if (i == columns.length - 1)
                        queryBuilder.append(columns[i]);
                    else
                        queryBuilder.append(columns[i])
                                .append(',');
                });

        queryBuilder.append(" FROM ")
                .append(table);

        Map<Integer, String> placeholders = null;
        if (leftWhereOperand != null) {
            placeholders = new HashMap<>();
            placeholders.put(1, leftWhereOperand);

            queryBuilder.append(" WHERE ")
                    .append(leftWhereOperand)
                    .append(" = ?");
        }

        return new QueryInfo(queryBuilder.toString(),placeholders);
    }


    public QueryInfo insert(String table, List<Field> classFields) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(table)
                .append(" (");

        Map<Integer, String> placeholders = new HashMap<>();

        StringBuilder names = new StringBuilder();
        StringBuilder questionMarks = new StringBuilder();
        for (int i = 0; i < classFields.size() - 1; i++) {
            String name = classFields.get(i).getName();
            names.append(name)
                    .append(", ");
            questionMarks.append("?")
                    .append(", ");

            placeholders.put(i, name);
        }

        queryBuilder.append(names.substring(0, names.length() - 2))
                .append(") VALUES (")
                .append(questionMarks.substring(0, questionMarks.length() - 2))
                .append(')');

        return new QueryInfo(queryBuilder.toString(), placeholders);
    }


    public String createCompanies(){
        StringBuilder builder = new StringBuilder("CREATE TABLE companies(");

        builder.append("id int NOT NULL AUTO_INCREMENT,\n" +
                "company_prefix varchar(255),\n" +
                "company_name varchar(255),\n" +
                "PRIMARY KEY(id),\n" +
                "UNIQUE KEY(company_prefix)");

        builder.append(");");
        return builder.toString();
    }

    public String createCities(String companyName){
        StringBuilder builder = new StringBuilder("CREATE TABLE "+ companyName +"_cities(");

        builder.append("    id int NOT NULL AUTO_INCREMENT,\n" +
                "    city_name varchar(255),\n" +
                "    PRIMARY KEY(id)");

        builder.append(");");
        return builder.toString();
    }
    public String createDepartments(String companyName){
        StringBuilder builder = new StringBuilder("CREATE TABLE "+ companyName +"_departments(");

        builder.append("    id int NOT NULL AUTO_INCREMENT,\n" +
                "    department_name varchar(255),\n" +
                "    department_city_id int,\n" +
                "    department_company_id int,\n" +
                "    \n" +
                "    \tFOREIGN KEY (department_city_id) REFERENCES "+ companyName + "_cities(id),\n" +
                "    \t/*FOREIGN KEY (department_company_id) REFERENCES _companies(id),*/\n" +
                "    PRIMARY KEY(id)");

        builder.append(");");
        return builder.toString();
    }

    public String createEmployees(String companyName){
        StringBuilder builder = new StringBuilder("CREATE TABLE "+ companyName +"_employees(");

        builder.append("    id int NOT NULL AUTO_INCREMENT,\n" +
                "    employee_name varchar(255),\n" +
                "    employee_city_id int,\n" +
                "    employee_department_id int,/*IF NEEDED*/\n" +
                "    employee_company_id int,\n" +
                "    PRIMARY KEY(id),\n" +
                "\tFOREIGN KEY (employee_company_id) REFERENCES companies(id),\n" +
                "\tFOREIGN KEY (employee_city_id) REFERENCES " + companyName + "_cities(id)");

        builder.append(");");
        return builder.toString();
    }

    public String createReports(String companyName){
        StringBuilder builder = new StringBuilder("CREATE TABLE "+ companyName +"_reports(");

        builder.append("    id int NOT NULL AUTO_INCREMENT,\n" +
                "    report_date DATE,\n" +
                "    report_turnover decimal,\n" +
                "    report_employee_id int,\n" +
                "    report_company_id int,\n" +
                "    PRIMARY KEY(id),\n" +
                "\tFOREIGN KEY (report_employee_id) REFERENCES "+ companyName + "_employees(id),\n" +
                "\tFOREIGN KEY (report_company_id) REFERENCES companies(id)   ");

        builder.append(");");
        return builder.toString();
    }

}
