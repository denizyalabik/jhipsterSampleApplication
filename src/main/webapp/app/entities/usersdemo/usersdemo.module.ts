import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    UsersdemoService,
    UsersdemoPopupService,
    UsersdemoComponent,
    UsersdemoDetailComponent,
    UsersdemoDialogComponent,
    UsersdemoPopupComponent,
    UsersdemoDeletePopupComponent,
    UsersdemoDeleteDialogComponent,
    usersdemoRoute,
    usersdemoPopupRoute,
    UsersdemoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...usersdemoRoute,
    ...usersdemoPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UsersdemoComponent,
        UsersdemoDetailComponent,
        UsersdemoDialogComponent,
        UsersdemoDeleteDialogComponent,
        UsersdemoPopupComponent,
        UsersdemoDeletePopupComponent,
    ],
    entryComponents: [
        UsersdemoComponent,
        UsersdemoDialogComponent,
        UsersdemoPopupComponent,
        UsersdemoDeleteDialogComponent,
        UsersdemoDeletePopupComponent,
    ],
    providers: [
        UsersdemoService,
        UsersdemoPopupService,
        UsersdemoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationUsersdemoModule {}
