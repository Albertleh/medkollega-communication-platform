export class DiagnosisDto {
  constructor(
    public id: string,
    public createdAt: string,       // LocalDate
    public diagnosisText: string,
    public icdCode: string | null,
    public validUntil: string | null
  ) {}
}
