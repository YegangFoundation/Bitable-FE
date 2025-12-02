package com.example.bitable_fe.core.data.repository.di

import com.example.bitable_fe.core.data.repository.iface.AccountRepository
import com.example.bitable_fe.core.data.repository.iface.AlertRepository
import com.example.bitable_fe.core.data.repository.iface.BankRepository
import com.example.bitable_fe.core.data.repository.iface.ChartRepository
import com.example.bitable_fe.core.data.repository.iface.CoinRepository
import com.example.bitable_fe.core.data.repository.iface.HoldingsRepository
import com.example.bitable_fe.core.data.repository.iface.MarketRepository
import com.example.bitable_fe.core.data.repository.iface.OrderRepository
import com.example.bitable_fe.core.data.repository.iface.PortfolioRepository
import com.example.bitable_fe.core.data.repository.iface.TransactionRepository
import com.example.bitable_fe.core.data.repository.iface.UserRepository
import com.example.bitable_fe.core.data.repository.iface.VoiceRepository
import com.example.bitable_fe.core.data.repository.impl.AccountRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.AlertRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.BankRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.ChartRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.CoinRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.HoldingsRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.MarketRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.OrderRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.PortfolioRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.TransactionRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.UserRepositoryImpl
import com.example.bitable_fe.core.data.repository.impl.VoiceRepositoryImpl
import com.example.bitable_fe.core.network.api.AccountApi
import com.example.bitable_fe.core.network.api.AlertApi
import com.example.bitable_fe.core.network.api.BankApi
import com.example.bitable_fe.core.network.api.ChartApi
import com.example.bitable_fe.core.network.api.CoinApi
import com.example.bitable_fe.core.network.api.MarketApi
import com.example.bitable_fe.core.network.api.OrderApi
import com.example.bitable_fe.core.network.api.PortfolioApi
import com.example.bitable_fe.core.network.api.TransactionApi
import com.example.bitable_fe.core.network.api.UserApi
import com.example.bitable_fe.core.network.api.VoiceApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi): UserRepository =
        UserRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAccountRepository(api: AccountApi): AccountRepository =
        AccountRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideBankRepository(api: BankApi): BankRepository =
        BankRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinApi): CoinRepository =
        CoinRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideOrderRepository(api: OrderApi): OrderRepository =
        OrderRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideHoldingsRepository(api: PortfolioApi): HoldingsRepository =
        HoldingsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTransactionRepository(api: TransactionApi): TransactionRepository =
        TransactionRepositoryImpl(api)

    @Provides
    @Singleton
    fun providePortfolioRepository(api: PortfolioApi): PortfolioRepository =
        PortfolioRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideChartRepository(api: ChartApi): ChartRepository =
        ChartRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideMarketRepository(api: MarketApi): MarketRepository =
        MarketRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAlertRepository(api: AlertApi): AlertRepository =
        AlertRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideVoiceRepository(api: VoiceApi): VoiceRepository =
        VoiceRepositoryImpl(api)
}
