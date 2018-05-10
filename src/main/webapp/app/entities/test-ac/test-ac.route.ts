import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TestAcComponent } from './test-ac.component';
import { TestAcDetailComponent } from './test-ac-detail.component';
import { TestAcPopupComponent } from './test-ac-dialog.component';
import { TestAcDeletePopupComponent } from './test-ac-delete-dialog.component';

@Injectable()
export class TestAcResolvePagingParams implements Resolve<any> {

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

export const testAcRoute: Routes = [
    {
        path: 'test-ac',
        component: TestAcComponent,
        resolve: {
            'pagingParams': TestAcResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestAcs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'test-ac/:id',
        component: TestAcDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestAcs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const testAcPopupRoute: Routes = [
    {
        path: 'test-ac-new',
        component: TestAcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestAcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-ac/:id/edit',
        component: TestAcPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestAcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-ac/:id/delete',
        component: TestAcDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TestAcs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
