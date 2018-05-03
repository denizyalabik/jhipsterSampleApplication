import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationCategoryModule } from './category/category.module';
import { JhipsterSampleApplicationProductModule } from './product/product.module';
import { JhipsterSampleApplicationCustomerModule } from './customer/customer.module';
import { JhipsterSampleApplicationAddressModule } from './address/address.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationCategoryModule,
        JhipsterSampleApplicationProductModule,
        JhipsterSampleApplicationCustomerModule,
        JhipsterSampleApplicationAddressModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
