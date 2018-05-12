import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AcademiaUsersComponent } from './academia-users.component';
import { AcademiaUsersDetailComponent } from './academia-users-detail.component';
import { AcademiaUsersPopupComponent } from './academia-users-dialog.component';
import { AcademiaUsersDeletePopupComponent } from './academia-users-delete-dialog.component';

@Injectable()
export class AcademiaUsersResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const academiaUsersRoute: Routes = [
    {
        path: 'academia-users',
        component: AcademiaUsersComponent,
        resolve: {
            'pagingParams': AcademiaUsersResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademiaUsers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'academia-users/:id',
        component: AcademiaUsersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademiaUsers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const academiaUsersPopupRoute: Routes = [
    {
        path: 'academia-users-new',
        component: AcademiaUsersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademiaUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'academia-users/:id/edit',
        component: AcademiaUsersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademiaUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'academia-users/:id/delete',
        component: AcademiaUsersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AcademiaUsers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
