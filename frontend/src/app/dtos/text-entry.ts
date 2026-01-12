export class TextEntryDto {
  constructor(
    public id: string,
    public createdAt: string,   // LocalDateTime
    public updatedAt: string,   // LocalDateTime
    public text: string
  ) {}
}
