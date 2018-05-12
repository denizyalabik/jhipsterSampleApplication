import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    HistoryService,
    HistoryPopupService,
    HistoryComponent,
    HistoryDetailComponent,
    HistoryDialogComponent,
    HistoryPopupComponent,
    HistoryDeletePopupComponent,
    HistoryDeleteDialogComponent,
    historyRoute,
    historyPopupRoute,
    HistoryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...historyRoute,
    ...historyPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        HistoryComponent,
        HistoryDetailComponent,
        HistoryDialogComponent,
        HistoryDeleteDialogComponent,
        HistoryPopupComponent,
        HistoryDeletePopupComponent,
    ],
    entryComponents: [
        HistoryComponent,
        HistoryDialogComponent,
        HistoryPopupComponent,
        HistoryDeleteDialogComponent,
        HistoryDeletePopupComponent,
    ],
    providers: [
        HistoryService,
        HistoryPopupService,
        HistoryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationHistoryModule {}
