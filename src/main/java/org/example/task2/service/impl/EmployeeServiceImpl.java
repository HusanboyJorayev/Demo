package org.example.task2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.task2.dto.EmployeeCalculationResponse;
import org.example.task2.dto.EmployeeDto;
import org.example.task2.dto.EmployeeOrganizationResponse;
import org.example.task2.dto.ResponseDto;
import org.example.task2.entity.Employee;
import org.example.task2.enums.CalculationType;
import org.example.task2.mapper.EmployeeMapper;
import org.example.task2.repository.EmployeeRepository;
import org.example.task2.repository.OrganizationRepository;
import org.example.task2.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final OrganizationRepository organizationRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public ResponseDto<?> create(EmployeeDto dto) {
        if (this.organizationRepository.findById(dto.getOrganizationId()).isPresent()) {
            Employee entity = this.employeeMapper.toEntity(dto);
            this.employeeRepository.save(entity);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .data(this.employeeMapper.toDto(entity))
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("ORGANIZATION is not found")
                .build();
    }

    @Override
    public ResponseDto<?> get(Integer id) {
        Optional<Employee> optional = this.employeeRepository.findById(id);
        if (optional.isPresent()) {
            Employee employee = optional.get();
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .data(this.employeeMapper.toDto(employee))
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("EMPLOYEE is not found")
                .build();
    }

    @Override
    public ResponseDto<?> update(EmployeeDto dto, Integer id) {
        if (this.organizationRepository.findById(dto.getOrganizationId()).isPresent()) {
            Optional<Employee> optional = this.employeeRepository.findById(id);
            if (optional.isPresent()) {
                Employee employee = optional.get();
                this.employeeMapper.update(dto, employee);
                this.employeeRepository.save(employee);
                return ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("SUCCESS")
                        .data(this.employeeMapper.toDto(employee))
                        .build();
            }
            return ResponseDto.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("EMPLOYEE is not found")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("ORGANIZATION is not found")
                .build();
    }

    @Override
    public ResponseDto<?> delete(Integer id) {
        Optional<Employee> optional = this.employeeRepository.findById(id);
        if (optional.isPresent()) {
            Employee employee = optional.get();
            this.employeeRepository.delete(employee);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("DELETED")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("EMPLOYEE is not found")
                .build();
    }

    @Override
    public ResponseDto<?> getAll() {
        List<Employee> list = this.employeeRepository.findAll();
        if (!list.isEmpty()) {
            List<EmployeeDto> response = this.employeeMapper.toDtoLis(list);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .data(response)
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NO_CONTENT)
                .message("LIST is empty")
                .build();
    }

    @Override
    public ResponseDto<?> avgAmount(LocalDate date) {
        String sql = "with short as (select avg(c.amount) as AvgAmount,\n" +
                "                      e.id          as employeeId\n" +
                "               from calculation as c\n" +
                "                        left join employee as e on e.id = c.employee_id\n" +
                "                        left join organization as o on o.id = c.organization_id\n" +
                "                        left join organization as o2 on o2.id = e.organization_id\n" +
                "                        left join region as r on o.region_id = r.id or o2.region_id = r.id\n" +
                "               group by e.id)\n" +
                "select e.id              as eId,\n" +
                "       e.organization_id as eOrganizationId,\n" +
                "       e.pinfl           as ePinfl,\n" +
                "       e.last_name       as eLastname,\n" +
                "       e.first_name      as eFirstname,\n" +
                "       e.hire_date       as eHireDate,\n" +
                "       o.name            as oName,\n" +
                "       o.region_id       as oRegionId,\n" +
                "       o.id              as oId,\n" +
                "       s.AvgAmount       as avgAmount\n" +
                "from calculation as c\n" +
                "          join employee as e on e.id = c.employee_id\n" +
                "          left join organization as o on o.id = c.organization_id\n" +
                "          left join organization as o2 on o2.id = e.organization_id\n" +
                "          join region as r on o.region_id = r.id or o2.region_id = r.id\n" +
                "          join short as s on s.employeeId = e.id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");
        var format = date.format(formatter);
        sql += " where DATE_FORMAT(c.date, '%Y.%m')=:format";
        params.addValue("format", format);

        List<EmployeeOrganizationResponse> responses = jdbcTemplate.query(sql, params, r -> {
            List<EmployeeOrganizationResponse> e = new ArrayList<>();
            while (r.next()) {
                int eId = r.getInt("eId");
                int eOrganizationId = r.getInt("eOrganizationId");
                String ePinfl = r.getString("ePinfl");
                String eLastname = r.getString("eLastname");
                String eFirstname = r.getString("eFirstname");
                Timestamp cDate = r.getTimestamp("eHireDate");
                LocalDate eLocalDate = null;
                if (cDate != null) {
                    eLocalDate = cDate.toLocalDateTime().toLocalDate();
                }
                String oName = r.getString("oName");
                int oRegionId = r.getInt("oRegionId");
                int oId = r.getInt("oId");
                double avgAmount = r.getDouble("avgAmount");
                e.add(
                        new EmployeeOrganizationResponse(
                                eId,
                                eOrganizationId,
                                ePinfl,
                                eLastname,
                                eFirstname,
                                eLocalDate,
                                oName,
                                oRegionId,
                                oId,
                                avgAmount
                        )
                );
            }
            return e;
        });
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .data(responses)
                .build();
    }

    @Override
    public ResponseDto<?> employeeSalaryVacation(LocalDate date) {
        String sql = "select e.id               as eId,\n" +
                "       e.organization_id  as eOrganizationId,\n" +
                "       e.pinfl            as ePinfl,\n" +
                "       e.last_name        as eLastname,\n" +
                "       e.first_name       as eFirstname,\n" +
                "       e.hire_date        as eHireDate,\n" +
                "       c.amount           as cAmount,\n" +
                "       c.calculation_type as cCalculationType\n" +
                "from calculation as c\n" +
                "         join employee as e on c.employee_id = e.id\n" +
                "where c.calculation_type in ('SALARY', 'VACATION')";
        MapSqlParameterSource params = new MapSqlParameterSource();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");
        var format = date.format(formatter);
        sql += " and DATE_FORMAT(c.date, '%Y.%m')=:format";
        params.addValue("format", format);

        List<EmployeeCalculationResponse.ShortInfo> response = jdbcTemplate.query(sql, params, r -> {
            List<EmployeeCalculationResponse.ShortInfo> sh = new ArrayList<>();
            while (r.next()) {
                int eId = r.getInt("eId");
                int eOrganizationId = r.getInt("eOrganizationId");
                String ePinfl = r.getString("ePinfl");
                String eLastname = r.getString("eLastname");
                String eFirstname = r.getString("eFirstname");
                Timestamp cDate = r.getTimestamp("eHireDate");
                LocalDate eLocalDate = null;
                if (cDate != null) {
                    eLocalDate = cDate.toLocalDateTime().toLocalDate();
                }
                double cAmount = r.getDouble("cAmount");
                CalculationType calculationType = null;
                if (r.getString("cCalculationType") != null) {
                    calculationType = CalculationType.valueOf(r.getString("cCalculationType"));
                }
                sh.add(
                        new EmployeeCalculationResponse.ShortInfo(
                                eId,
                                eOrganizationId,
                                ePinfl,
                                eFirstname,
                                eLastname,
                                eLocalDate,
                                cAmount,
                                calculationType
                        )
                );
            }
            return sh;
        });
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .data(response)
                .build();
    }
}
