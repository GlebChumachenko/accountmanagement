package com.accountmanagement.controller;

import com.accountmanagement.controller.rest.AccountsRestCont;
import com.accountmanagement.controller.rest.CostsRestCont;
import com.accountmanagement.controller.rest.ProfilesRestCont;
import com.accountmanagement.controller.rest.RegRestCont;
import com.accountmanagement.dto.OperationResponse;
import com.accountmanagement.model.Accounts;
import com.accountmanagement.model.Costs;
import com.accountmanagement.model.Users;
import com.accountmanagement.model.enums.AccountCategory;
import com.accountmanagement.model.enums.CostsCategory;
import com.accountmanagement.model.enums.Role;
import com.accountmanagement.repo.AccountsRepo;
import com.accountmanagement.repo.CostsRepo;
import com.accountmanagement.repo.UsersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class MainControllersTest {

    @Mock
    AccountsRepo accountsRepo;

    @Mock
    UsersRepo usersRepo;

    @Mock
    CostsRepo costsRepo;

    @InjectMocks
    AccountsRestCont accountsRestController;

    @InjectMocks
    ProfilesRestCont profilesRestController;

    @InjectMocks
    RegRestCont regRestController;

    @InjectMocks
    CostsRestCont costsRestCont;

    MockMvc mockMvc;


    JacksonTester<List<Accounts>> jsonAccountList;
    JacksonTester<Map<String, Accounts>> jsonAccountMap;
    JacksonTester<Users> jsonUser;

    JacksonTester<List<Costs>> jsonCostsList;

    JacksonTester<OperationResponse> jsonOperationResponse;

    @Captor
    ArgumentCaptor<Accounts> accountsArgumentCaptor;

    @Captor
    ArgumentCaptor<Users> usersArgumentCaptor;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders
                .standaloneSetup(
                        accountsRestController,
                        profilesRestController,
                        costsRestCont,
                        regRestController)
                .build();
    }

    private Accounts createTestAccount() {
        return Accounts
                .builder()
                .name("TestAcc")
                .sum(100)
                .date("TestDate")
                .category(AccountCategory.SELL)
                .build();
    }

    private Users createUser() {
        return Users
                .builder()
                .fio("TestFio")
                .role(Role.ADMIN)
                .username("TestUsername")
                .password("TestPassword")
                .build();
    }

    @Test
    void shouldReturnAllAccounts() throws Exception {
        List<Accounts> accountsList = List.of(createTestAccount());
        when(accountsRepo.findAll()).thenReturn(accountsList);

        String expected = jsonAccountList.write(accountsList).getJson();

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.get("/rest/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldEditAccount() throws Exception {
        Accounts testAccount = createTestAccount();
        when(accountsRepo.getReferenceById(anyLong())).thenReturn(testAccount);
        String name = "TestNameUpdated";
        float sum = 50;
        String date = "TestDateUpdated";
        AccountCategory category = AccountCategory.BUY;
        when(accountsRepo.save(any())).thenReturn(Accounts.builder().name("Saved Account").build());

        String expected = jsonAccountMap.write(Collections.singletonMap("Updated Account: ", Accounts.builder().name("Saved Account").build())).getJson();

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/accounts/10/edit")
                        .param("name", name)
                        .param("sum", String.valueOf(sum))
                        .param("date", date)
                        .param("category", String.valueOf(category))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        verify(accountsRepo, times(1)).save(accountsArgumentCaptor.capture());

        assertThat(expected).isEqualTo(actual);

        Accounts expectedToSave = Accounts
                .builder()
                .name(name)
                .sum(sum)
                .date(date)
                .category(category)
                .build();
        assertThat(accountsArgumentCaptor.getValue()).usingRecursiveComparison().ignoringFields("password").isEqualTo(expectedToSave);

    }

    @Test
    void shouldEditUser() throws Exception {
        Users user = createUser();
        when(usersRepo.getReferenceById(anyLong())).thenReturn(user);
        Role testRole = Role.MANAGER;
        when(usersRepo.save(any())).thenReturn(Users.builder().fio("Saved User").build());

        String expected = jsonUser.write(Users.builder().fio("Saved User").build()).getJson();

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/profiles/edit/10")
                        .param("role", String.valueOf(testRole))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        verify(usersRepo, times(1)).save(usersArgumentCaptor.capture());

        assertThat(expected).isEqualTo(actual);

        user.setRole(testRole);
        assertThat(usersArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(user);

    }

    @Test
    void shouldReturnAllCosts() throws Exception {
        List<Costs> costsList = List.of(Costs
                .builder()
                .category(CostsCategory.OTHER)
                .date("TestDate")
                .name("TestCost")
                .sum(100)
                .build());
        when(costsRepo.findAll()).thenReturn(costsList);

        String expected = jsonCostsList.write(costsList).getJson();

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.get("/rest/costs")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldRegisterUsers() throws Exception {
        when(usersRepo.findAll()).thenReturn(Collections.emptyList());

        String fio = "testFio";
        String userName="testUserName";
        String pwd = "testPassword";

        String actual = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/reg")
                        .param("fio", fio)
                        .param("username", userName)
                        .param("password", pwd)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        verify(usersRepo, times(1)).save(usersArgumentCaptor.capture());

        Users expectedUserToSave = Users
                .builder()
                .fio(fio)
                .username(userName)
                .password(pwd)
                .role(Role.ADMIN)
                .build();
        assertThat(usersArgumentCaptor.getValue()).usingRecursiveComparison().ignoringFields("password").isEqualTo(expectedUserToSave);

        String expected = jsonOperationResponse.write(OperationResponse.builder().status(OperationResponse.Status.SUCCESS).build()).getJson();
        assertThat(actual).isEqualTo(expected);
    }


}