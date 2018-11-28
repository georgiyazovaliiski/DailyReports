package connectionResources;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    public QueryInfo select(String[] columns, String table, String dateWhereOperand1, boolean dateSelect) {
        if(!dateSelect){
            return select(columns,table,dateWhereOperand1);
        }
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
        String[] companyAttributes = table.split("_");
        queryBuilder.append(" JOIN companies ON companies.`id` = report_company_id\n" +
                "JOIN " + companyAttributes[0] + "_cities ON " + companyAttributes[0] + "_cities.`id` = report_city_id\n");
                queryBuilder.append("JOIN " + companyAttributes[0] + "_employees ON " + companyAttributes[0] + "_employees.`id` = report_employee_id\n");

        for (String column : columns) {
            if(column.contains("DepartmentName")){
                queryBuilder.append("JOIN " + companyAttributes[0] + "_departments ON " + companyAttributes[0] + "_departments.id = report_department_id\n");
            }
        }

        Map<Integer, String> placeholders = null;
        if (dateWhereOperand1 != null) {
            placeholders = new HashMap<>();
            placeholders.put(1, dateWhereOperand1);

            queryBuilder.append(" WHERE ")
                    .append(dateWhereOperand1)
                    .append(" > ?");
        }

        if (dateWhereOperand1 != null) {
            placeholders.put(2, dateWhereOperand1);

            queryBuilder.append(" AND ")
                    .append(dateWhereOperand1)
                    .append(" <= ?");
        }
        System.out.println(queryBuilder.toString());
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
        System.out.println(queryBuilder.toString());
        return new QueryInfo(queryBuilder.toString(), placeholders);
    }

    public QueryInfo update(String[] columns, String table, String leftWhereOperand) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE " + table + " SET ");

        if (columns == null || columns.length < 1)
            return null;


        IntStream.range(0, columns.length)
                .forEach(i -> {
                    if (i == columns.length - 1)
                        queryBuilder.append(columns[i] + " = ?");
                    else
                        queryBuilder.append(columns[i] + " = ?")
                                .append(',');
                });

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

    public String checkForDepartmentsTable(String companyName){
        String query = "SELECT COUNT(*)\n" +
                "FROM information_schema.tables \n" +
                "WHERE table_schema = \"hyperreports\" \n" +
                "AND table_name = \""+ companyName+"_departments\";";
        System.out.println(query);
        return query;
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
                //"    employee_department_id int,/*IF NEEDED*/\n" +
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
                //"    report_department_id int,\n" +
                "    report_city_id int,\n" +
                "    PRIMARY KEY(id),\n" +
                "\tFOREIGN KEY (report_employee_id) REFERENCES "+ companyName + "_employees(id),\n" +
                "\tFOREIGN KEY (report_company_id) REFERENCES companies(id),   " +
                "\tFOREIGN KEY (report_city_id) REFERENCES " + companyName + "_cities(id)   ");

        builder.append(");");
        return builder.toString();
    }

    public String addForeignKey(String company, String table, String type){

        String query = "ALTER TABLE " + table +
        " ADD "+ type +"_department_id int," +
        " ADD CONSTRAINT " + "fk_"+type+"_"+ company +"_department_id" + " FOREIGN KEY (" + type + "_department_id" + ") REFERENCES " + company + "_" + type + "s(id);";
        System.out.println("Pravim query: " + query);
        return query;
    }

}
