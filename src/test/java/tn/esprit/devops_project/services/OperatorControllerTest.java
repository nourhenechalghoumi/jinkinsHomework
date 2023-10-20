package tn.esprit.devops_project.services;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import tn.esprit.devops_project.entities.Operator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/data-set/stock-data.xml") // Load initial test data
@DatabaseTearDown("/data-set/stock-data.xml") // Cleanup after tests
class OperatorControllerTest {

    @Autowired
    private OperatorServiceImpl operatorController;
    @Test
    void getOperators() {
        List<Operator> operators = this.operatorController.retrieveAllOperators();
        assertEquals(2, operators.size());
    }

    @Test
    void retrieveOperator() {
        Operator operator = operatorController.retrieveOperator(1L);
        assertEquals("Operator 1", operator.getFname());
    }

    @Test
    void addOperator() {
        Operator newOperator = new Operator();
        newOperator.setFname("New Operator");

        Operator addedOperator = operatorController.addOperator(newOperator);

        assertEquals("New Operator", addedOperator.getFname());
    }
    @Test
    void modifyOperator() {
        Operator operator = new Operator();
        operator.setIdOperateur(1L);
        operator.setFname("Modified Operator");

        Operator modifiedOperator = operatorController.updateOperator(operator);

        assertEquals("Modified Operator", modifiedOperator.getFname());
    }
}
