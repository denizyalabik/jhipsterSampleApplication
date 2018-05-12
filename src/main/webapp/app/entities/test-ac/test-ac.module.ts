import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    TestAcService,
    TestAcPopupService,
    TestAcComponent,
    TestAcDetailComponent,
    TestAcDialogComponent,
    TestAcPopupComponent,
    TestAcDeletePopupComponent,
    TestAcDeleteDialogComponent,
    testAcRoute,
    testAcPopupRoute,
    TestAcResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...testAcRoute,
    ...testAcPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TestAcComponent,
        TestAcDetailComponent,
        TestAcDialogComponent,
        TestAcDeleteDialogComponent,
        TestAcPopupComponent,
        TestAcDeletePopupComponent,
    ],
    entryComponents: [
        TestAcComponent,
        TestAcDialogComponent,
        TestAcPopupComponent,
        TestAcDeleteDialogComponent,
        TestAcDeletePopupComponent,
    ],
    providers: [
        TestAcService,
        TestAcPopupService,
        TestAcResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTestAcModule {}
