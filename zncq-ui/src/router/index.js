import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login";
import SystemMenu from "@/views/SystemMenu";
import DemoV from "@/views/DemoV";
import Purchase from "@/views/Purchase";
import PurchaseOrder from "@/views/PurchaseOrder";
import Raw from "@/views/Raw";
import Supplier from "@/views/Supplier";
import Warehouse from "@/views/Warehouse";
import Goods from "@/views/Goods"
import Transfer from "@/views/Transfer";
import AvailableContainer from "@/views/AvailableContainer";
import Container from "@/views/Container";
import Vehicle from "@/views/Vehicle";
import VehicleDispatching from "@/views/VehicleDispatching";
import Receipt from "@/views/Receipt";
import Checkout from "@/views/Checkout";
import CheckoutNotice from "@/views/CheckoutNotice";
import CheckoutState from "@/views/CheckoutState";
import WarehouseTotal from "@/views/WarehouseTotal";
import Checkin from "@/views/Checkin";
import CheckinNotice from "@/views/CheckinNotice";
import CheckinState from "@/views/CheckinState";
import RawWarn from "@/views/RawWarn";
import Equip from "@/views/Equip";
import EquipStatistics from "@/views/EquipStatistics";
import Map from "@/components/Map";
import ContractManager from "@/views/ContractManager"
import TicketManager from "@/views/TicketManager";
import ContractSign from "@/views/ContractSign";
import UserManager from "@/views/UserManager";
import Customer from "@/views/Customer";
import RoleManager from "@/views/RoleManager";
import LogManager from "@/views/LogManager";
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '登录',
    component:Login
  },
  {
    path: '/main',
    name: '首页',
    component: SystemMenu,
    meta:{isAuth:true},
    children:[
      {
        path:'/main/map',
        name:'订单监控',
        component:Map,
        meta:{isAuth:true},
      },
      {
        path:'/main/customer',
        name:'客户管理',
        component:Customer,
        meta:{isAuth:true},
      },
      {
        path:'/main/logManager',
        name:'日志信息',
        component:LogManager,
        meta:{isAuth:true},
      },
      {
        path:'/main/roleManager',
        name:'角色',
        component:RoleManager,
        meta:{isAuth:true},
      },
      {
        path:'/main/userManager',
        name:'用户管理',
        component:UserManager,
        meta:{isAuth:true},
      },
      {
        path:'/main/demov',
        name:'测试',
        component:DemoV,
        meta:{isAuth:true},
      },
      {
        path: '/main/purchase',
        name: '采购订单',
        component: Purchase,
        meta:{isAuth:true},
      },
      {
        path: '/main/purchaseOrder',
        name: '订单审核',
        component: PurchaseOrder,
        meta:{isAuth:true},
      },
      {
        path: '/main/raw',
        name: '原料信息',
        component: Raw,
        meta:{isAuth:true},
      },
      {
        path: '/main/supplier',
        name: '供应商信息',
        component: Supplier,
        meta:{isAuth:true},
      },
      {
        path: '/main/warehouse',
        name: '仓库信息',
        component: Warehouse,
        meta:{isAuth:true},
      },
      {
        path: '/main/goods',
        name: '物品信息',
        component: Goods,
        meta:{isAuth:true},
      },
      {
        path: '/main/transfer',
        name: '移库管理',
        component: Transfer,
        meta:{isAuth:true},
      },
      {
        path: '/main/availableContainer',
        name: '可用储位',
        component: AvailableContainer,
        meta:{isAuth:true},
      },
      {
        path: '/main/container',
        name: '库柜管理',
        component: Container,
        meta:{isAuth:true},
      },
      {
        path: '/main/vehicle',
        name: '运输车辆信息',
        component: Vehicle,
        meta:{isAuth:true},
      },
      {
        path: '/main/vehicleDispatching',
        name: '派车信息',
        component: VehicleDispatching,
        meta:{isAuth:true},
      },
      {
        path: '/main/receipt',
        name: '回单管理',
        component: Receipt,
        meta:{isAuth:true},
      },
      {
        path: '/main/checkout',
        name: '出库登记',
        component: Checkout,
        meta:{isAuth:true},
      },
      {
        path: '/main/checkoutNotice',
        name: '出库通知',
        component: CheckoutNotice,
        meta:{isAuth:true},
      },
      {
        path: '/main/checkoutState',
        name: '出库订单状态',
        component: CheckoutState,
        meta:{isAuth:true},
      },
      {
        path: '/main/warehouseTotal',
        name: '库存总表',
        component: WarehouseTotal,
        meta:{isAuth:true},
      },
      {
        path: '/main/checkin',
        name:'入库登记',
        component: Checkin,
        meta:{isAuth:true},
      },
      {
        path: '/main/checkinNotice',
        name:'入库通知',
        component: CheckinNotice,
        meta:{isAuth:true},
      },
      {
        path: '/main/CheckinState',
        name:'入库订单状态',
        component: CheckinState,
        meta:{isAuth:true},
      },
      {
        path: '/main/rawWarn',
        name:'预警管理',
        component: RawWarn,
        meta:{isAuth:true},
      },
      {
        path: '/main/equip',
        name:'设备信息',
        component: Equip,
        meta:{isAuth:true},
      },
      {
        path: '/main/equipStatistics',
        name:'设备统计',
        component: EquipStatistics,
        meta:{isAuth:true},
      },
      {
        path: '/main/contractManager',
        name:'合同维护',
        component: ContractManager,
        meta:{isAuth:true},
      },
      {
        path: '/main/ticketManager',
        name:'票据管理',
        component: TicketManager,
        meta:{isAuth:true},
      },
      {
        path: '/main/contractSign',
        name:'合同签署',
        component: ContractSign,
        meta:{isAuth:true},
      }
    ]
  }

]

const router = new VueRouter({
  routes:routes,
  mode:'history'

})


export default router
