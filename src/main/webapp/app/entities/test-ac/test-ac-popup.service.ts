import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TestAc } from './test-ac.model';
import { TestAcService } from './test-ac.service';

@Injectable()
export class TestAcPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private testAcService: TestAcService

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
                this.testAcService.find(id)
                    .subscribe((testAcResponse: HttpResponse<TestAc>) => {
                        const testAc: TestAc = testAcResponse.body;
                        if (testAc.createdTime) {
                            testAc.createdTime = {
                                year: testAc.createdTime.getFullYear(),
                                month: testAc.createdTime.getMonth() + 1,
                                day: testAc.createdTime.getDate()
                            };
                        }
                        this.ngbModalRef = this.testAcModalRef(component, testAc);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.testAcModalRef(component, new TestAc());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    testAcModalRef(component: Component, testAc: TestAc): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.testAc = testAc;
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
