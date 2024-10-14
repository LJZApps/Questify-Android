package de.ljz.questify.core.di

import de.ljz.questify.data.emitter.ErrorEmitter
import de.ljz.questify.data.emitter.NetworkErrorEmitter
import org.koin.dsl.module

val emitterModule = module {
    single<NetworkErrorEmitter> { NetworkErrorEmitter() }

    single<ErrorEmitter> { ErrorEmitter() }
}