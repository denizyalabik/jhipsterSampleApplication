import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    AcademiaUsersService,
    AcademiaUsersPopupService,
    AcademiaUsersComponent,
    AcademiaUsersDetailComponent,
    AcademiaUsersDialogComponent,
    AcademiaUsersPopupComponent,
    AcademiaUsersDeletePopupComponent,
    AcademiaUsersDeleteDialogComponent,
    academiaUsersRoute,
    academiaUsersPopupRoute,
    AcademiaUsersResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...academiaUsersRoute,
    ...academiaUsersPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AcademiaUsersComponent,
        AcademiaUsersDetailComponent,
        AcademiaUsersDialogComponent,
        AcademiaUsersDeleteDialogComponent,
        AcademiaUsersPopupComponent,
        AcademiaUsersDeletePopupComponent,
    ],
    entryComponents: [
        AcademiaUsersComponent,
        AcademiaUsersDialogComponent,
        AcademiaUsersPopupComponent,
        AcademiaUsersDeleteDialogComponent,
        AcademiaUsersDeletePopupComponent,
    ],
    providers: [
        AcademiaUsersService,
        AcademiaUsersPopupService,
        AcademiaUsersResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationAcademiaUsersModule {}
