package com.tian.darkhorse.domain

import com.tian.darkhorse.presentation.model.Ticket
import com.tian.darkhorse.data.network.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTicketDataUseCase @Inject constructor() {
    operator fun invoke(): Flow<Result<List<Ticket>>> = flowOf(
        Result.success(
            listOf(
                Ticket(
                    "TC-4521",
                    "1",
                    "airline1",
                    "12:35 pm",
                    "18:20 pm",
                    "+08:00",
                    "BeiJing",
                    "Sydney",
                    "800$",
                    false,
                    true
                ), Ticket(
                    "MK-2461",
                    "2",
                    "airline2",
                    "13:30 pm",
                    "17:10 pm",
                    "+08:00",
                    "ShangHai",
                    "Sydney",
                    "600$",
                    false,
                    true
                ),
                Ticket(
                    "FD-2241",
                    "3",
                    "airline3",
                    "12:30 pm",
                    "14:10 pm",
                    "+08:00",
                    "ShangHai",
                    "GuangZhou",
                    "600$",
                    false,
                    true
                ),
                Ticket(
                    "HX-1102",
                    "4",
                    "airline4",
                    "15:30 pm",
                    "23:10 pm",
                    "+08:00",
                    "ShenZhen",
                    "Sydney",
                    "2600$",
                    false,
                    true
                ),
                Ticket(
                    "FK-1228",
                    "5",
                    "airline5",
                    "12:30 pm",
                    "13:10 pm",
                    "+08:00",
                    "ShangHai",
                    "ShenZhen",
                    "600$",
                    false,
                    true
                )
            )
        )
    )
}