/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { MouvementComponent } from '../../../../../../main/webapp/app/entities/mouvement/mouvement.component';
import { MouvementService } from '../../../../../../main/webapp/app/entities/mouvement/mouvement.service';
import { Mouvement } from '../../../../../../main/webapp/app/entities/mouvement/mouvement.model';

describe('Component Tests', () => {

    describe('Mouvement Management Component', () => {
        let comp: MouvementComponent;
        let fixture: ComponentFixture<MouvementComponent>;
        let service: MouvementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [MouvementComponent],
                providers: [
                    MouvementService
                ]
            })
            .overrideTemplate(MouvementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MouvementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MouvementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Mouvement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mouvements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
