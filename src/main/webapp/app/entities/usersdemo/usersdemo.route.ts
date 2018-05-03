import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { UsersdemoComponent } from './usersdemo.component';
import { UsersdemoDetailComponent } from './usersdemo-detail.component';
import { UsersdemoPopupComponent } from './usersdemo-dialog.component';
import { UsersdemoDeletePopupComponent } from './usersdemo-delete-dialog.component';

@Injectable()
export class UsersdemoResolvePagingParams implements Resolve<any> {

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

export const usersdemoRoute: Routes = [
    {
        path: 'usersdemo',
        component: UsersdemoComponent,
        resolve: {
            'pagingParams': UsersdemoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usersdemos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'usersdemo/:id',
        component: UsersdemoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usersdemos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const usersdemoPopupRoute: Routes = [
    {
        path: 'usersdemo-new',
        component: UsersdemoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usersdemos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'usersdemo/:id/edit',
        component: UsersdemoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usersdemos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'usersdemo/:id/delete',
        component: UsersdemoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Usersdemos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
