/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aut.utcluj.isp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author stefan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        aut.utcluj.isp.ex1.TicketTest.class,
        aut.utcluj.isp.ex2.AirplaneTicketTest.class,
        aut.utcluj.isp.ex3.AirplaneTicketControllerTest.class,
        aut.utcluj.isp.ex4.AirplaneTicketControllerTest.class
})
public class ColocviuTestSuite {

}
