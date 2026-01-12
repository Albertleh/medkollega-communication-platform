import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { ChatRequestDto } from "src/app/dtos/chat-request";
import { ChatRequestService } from "src/app/services/chat.service.service";
import { PatientService } from "src/app/services/patient.service";
import { PatientDetailDto } from "src/app/dtos/patient-detail";
import { PatientDto } from "src/app/dtos/patient";
import { forkJoin, throwError } from "rxjs";
import { of } from "rxjs";
import { switchMap, tap, finalize, catchError } from "rxjs/operators";
import { PatientModificationDto } from "src/app/dtos/patient-modification";
import { Router } from "@angular/router";


@Component({
  selector: 'app-chat-request-popup',
  templateUrl: './chat-request-popup.component.html',
  standalone: false
})
export class ChatRequestPopupComponent implements OnInit {

  requests: ChatRequestDto[] = [];
  loading = true;

  accepting = new Set<string>();


  patientDetailMap = new Map<string, PatientDetailDto>();

  constructor(
    private chatRequestService: ChatRequestService,
    private patientService: PatientService,
    private router: Router
  ) {}

  ngOnInit() {
    this.load();
  }

  private formatSv(insurance: number, birthDate: string): string {
    const d = new Date(birthDate);
    const dd = String(d.getDate()).padStart(2, "0");
    const mm = String(d.getMonth() + 1).padStart(2, "0");
    const yy = String(d.getFullYear()).slice(-2);
    return `${insurance}${dd}${mm}${yy}`;
  }

  load() {
    this.chatRequestService.getAllOpenRequests().subscribe({
      next: (data: any) => {

        this.requests = Array.isArray(data?.chatRequests)
          ? data.chatRequests
          : [];

        this.patientService.getpatients().subscribe({
          next: (patients: PatientDto[]) => {

            const detailCalls = patients.map(p =>
              this.patientService.getPatient(p.id)
            );

            forkJoin(detailCalls).subscribe({
              next: (details: PatientDetailDto[]) => {

                this.patientDetailMap.clear();

                details.forEach(det => {
                  const fullSV = this.formatSv(
                    det.insuranceNumber,
                    det.dateOfBirth
                  );

                  this.patientDetailMap.set(fullSV, det);
                });

                this.loading = false;
              },

              error: (err) => {
                console.error("ERROR loading patient details:", err);
                this.loading = false;
              }
            });
          },

          error: err => {
            console.error("ERROR loading patient list:", err);
            this.loading = false;
          }
        });

        this.chatRequestService.markAllAsRead().subscribe();
      },

      error: err => {
        console.error("CHAT API ERROR:", err);
        this.loading = false;
      }
    });
  }


  patientExists(req: ChatRequestDto): boolean {
    const fullSV = this.formatSv(
      req.patientInsuranceNumber,
      req.patientDateOfBirth
    );
    return this.patientDetailMap.has(fullSV);
  }

  getExistingPatient(req: ChatRequestDto): PatientDetailDto | undefined {
    const fullSV = this.formatSv(
      req.patientInsuranceNumber,
      req.patientDateOfBirth
    );
    return this.patientDetailMap.get(fullSV);
  }

  private toYyyyMmDd(dateStr: string): string {
    const d = new Date(dateStr);
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, "0");
    const dd = String(d.getDate()).padStart(2, "0");
    return `${yyyy}-${mm}-${dd}`;
  }

  accept(req: ChatRequestDto) {
    console.log("ACCEPT CLICKED", req);

    if (this.accepting.has(req.chatId)) return;
    this.accepting.add(req.chatId);

    const chatType = req.groupChat ? 'group' : 'direct';
    const sozNr = this.toSozNr(req.patientInsuranceNumber, req.patientDateOfBirth);

    const patient$ = this.patientService.getPatientBySozNr(sozNr).pipe(
      // if patient not found -> create
      catchError(err => {
        console.warn("getPatientBySozNr failed, creating patient instead:", err?.status, err?.error ?? err);

        return this.patientService.createPatient(
          new PatientModificationDto(
            req.patientInsuranceNumber,
            this.toYyyyMmDd(req.patientDateOfBirth),
            "Unbekannt",
            "Unbekannt",
            "0000",
            "Unbekannt",
            "Unbekannt",
            "0",
            ""
          )
        );
      })
    );

    patient$
      .pipe(
        switchMap((patient: PatientDetailDto) =>
          this.chatRequestService.accept(req.chatId, patient.id).pipe(
            tap(() => {
              this.requests = this.requests.filter(r => r.chatId !== req.chatId);

              // navigate first, then refresh sidebar (because sidebar mounts on /patients)
              this.router.navigate(
                ['/patients', patient.id],
                { queryParams: { openChatId: req.chatId, openChatType: chatType } }
              ).then(() => this.patientService.requestRefresh());
            })
          )
        ),
        finalize(() => this.accepting.delete(req.chatId))
      )
      .subscribe({
        error: err => console.error("ACCEPT FLOW ERROR:", err?.status, err?.error ?? err)
      });
  }




  reject(req: ChatRequestDto) {
    this.chatRequestService
      .reject(req.chatId)
      .subscribe({
        next: () => {
          this.requests = this.requests.filter(r => r.chatId !== req.chatId);
        },
        error: err => console.error("REJECT ERROR:", err)
      });
  }

  @Output() close = new EventEmitter<void>();

  closeModal(): void {
    this.close.emit();
  }


  private toSozNr(insurance: number, dob: string): string {
    // backend expects yyyy-MM-dd
    const ymd = this.toYyyyMmDd(dob);
    return `${insurance}-${ymd}`;
  }



}
