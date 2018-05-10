import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AcademiaUsers } from './academia-users.model';
import { AcademiaUsersService } from './academia-users.service';

@Injectable()
export class AcademiaUsersPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private academiaUsersService: AcademiaUsersService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.academiaUsersService.find(id)
                    .subscribe((academiaUsersResponse: HttpResponse<AcademiaUsers>) => {
                        const academiaUsers: AcademiaUsers = academiaUsersResponse.body;
                        this.ngbModalRef = this.academiaUsersModalRef(component, academiaUsers);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.academiaUsersModalRef(component, new AcademiaUsers());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    academiaUsersModalRef(component: Component, academiaUsers: AcademiaUsers): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.academiaUsers = academiaUsers;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
